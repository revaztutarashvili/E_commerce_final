package com.nabiji.ecommerce.service.impl;

import com.nabiji.ecommerce.dto.response.DailySalesReportResponse;
import com.nabiji.ecommerce.dto.response.SalesSummaryResponse;
import com.nabiji.ecommerce.entity.Branch;
import com.nabiji.ecommerce.entity.DailySalesReport;
import com.nabiji.ecommerce.entity.Order;
import com.nabiji.ecommerce.enums.OrderStatus;
import com.nabiji.ecommerce.exception.ResourceNotFoundException;
import com.nabiji.ecommerce.mapper.ReportMapper;
import com.nabiji.ecommerce.repository.BranchRepository;
import com.nabiji.ecommerce.repository.DailySalesReportRepository;
import com.nabiji.ecommerce.repository.OrderRepository;
import com.nabiji.ecommerce.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    private final DailySalesReportRepository dailySalesReportRepository;
    private final OrderRepository orderRepository;
    private final BranchRepository branchRepository;
    private final ReportMapper reportMapper;

    public ReportServiceImpl(DailySalesReportRepository dailySalesReportRepository, OrderRepository orderRepository, BranchRepository branchRepository, ReportMapper reportMapper) {
        this.dailySalesReportRepository = dailySalesReportRepository;
        this.orderRepository = orderRepository;
        this.branchRepository = branchRepository;
        this.reportMapper = reportMapper;
    }

    @Override
    public DailySalesReportResponse getDailySalesReport(LocalDate date, Long branchId) {
        DailySalesReport report = dailySalesReportRepository.findByDateAndBranchId(date, branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Daily report not found for date " + date + " and branch " + branchId));
        return reportMapper.toDailySalesReportResponse(report);
    }

    @Override
    public SalesSummaryResponse getSalesSummary() {
        List<Order> allOrders = orderRepository.findAll();
        BigDecimal totalRevenue = allOrders.stream()
                .filter(o -> o.getStatus() == OrderStatus.COMPLETED)
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        SalesSummaryResponse summary = new SalesSummaryResponse();
        summary.setTotalRevenueAllBranches(totalRevenue);
        summary.setTotalOrdersAllBranches(allOrders.size());
        return summary;
    }

    @Override
    @Async // Run this task in a separate thread
    public void generateDailySalesReportForAllBranches(LocalDate date) {
        logger.info("Starting daily sales report generation for date: {}", date);
        List<Branch> branches = branchRepository.findAll();
        for (Branch branch : branches) {
            List<Order> orders = orderRepository.findAllByBranchIdAndStatusAndCreatedAtBetween(
                    branch.getId(),
                    OrderStatus.COMPLETED,
                    date.atStartOfDay(),
                    date.atTime(LocalTime.MAX)
            );

            BigDecimal totalSales = orders.stream()
                    .map(Order::getTotalAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            DailySalesReport report = new DailySalesReport();
            report.setBranch(branch);
            report.setDate(date);
            report.setTotalOrders(orders.size());
            report.setTotalSales(totalSales);

            dailySalesReportRepository.save(report);
            logger.info("Report generated for branch: {}, Date: {}, Total Sales: {}", branch.getName(), date, totalSales);
        }
        logger.info("Finished daily sales report generation for date: {}", date);
    }
    @Override
    @Async
    @Transactional
    public void generateSingleBranchReport(Branch branch, LocalDate date) {
        logger.info("Generating report for branch: '{}' on a separate thread.", branch.getName());
        List<Order> orders = orderRepository.findAllByBranchIdAndStatusAndCreatedAtBetween(
                branch.getId(),
                OrderStatus.COMPLETED,
                date.atStartOfDay(),
                date.atTime(LocalTime.MAX)
        );

        BigDecimal totalSales = orders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        DailySalesReport report = new DailySalesReport();
        report.setBranch(branch);
        report.setDate(date);
        report.setTotalOrders(orders.size());
        report.setTotalSales(totalSales);

        dailySalesReportRepository.save(report);
        logger.info("Successfully generated report for branch: '{}', Total Sales: {}", branch.getName(), totalSales);
    }
}