package com.nabiji.ecommerce.mapper;

import com.nabiji.ecommerce.dto.response.DailySalesReportResponse;
import com.nabiji.ecommerce.entity.DailySalesReport;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper {
    // This mapper will be simple for now as the DTO and Entity are very similar.
    // We can add more complex logic here later (e.g., mapping top products).
    public DailySalesReportResponse toDailySalesReportResponse(DailySalesReport report) {
        if (report == null) {
            return null;
        }
        DailySalesReportResponse response = new DailySalesReportResponse();
        response.setBranchId(report.getBranch().getId());
        response.setBranchName(report.getBranch().getName());
        response.setDate(report.getDate());
        response.setTotalSales(report.getTotalSales());
        response.setTotalOrders(report.getTotalOrders());
        // Top products logic would be added here
        return response;
    }
}