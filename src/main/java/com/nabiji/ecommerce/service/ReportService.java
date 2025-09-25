package com.nabiji.ecommerce.service;

import com.nabiji.ecommerce.dto.response.DailySalesReportResponse;
import com.nabiji.ecommerce.dto.response.SalesSummaryResponse;
import com.nabiji.ecommerce.entity.Branch;

import java.time.LocalDate;

public interface ReportService {
    DailySalesReportResponse getDailySalesReport(LocalDate date, Long branchId);
    SalesSummaryResponse getSalesSummary();
    void generateDailySalesReportForAllBranches(LocalDate date); // For Scheduler
    void generateSingleBranchReport(Branch branch, LocalDate date);

}