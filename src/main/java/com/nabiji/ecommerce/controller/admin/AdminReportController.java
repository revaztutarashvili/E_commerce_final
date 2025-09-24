package com.nabiji.ecommerce.controller.admin;

import com.nabiji.ecommerce.dto.response.DailySalesReportResponse;
import com.nabiji.ecommerce.dto.response.SalesSummaryResponse;
import com.nabiji.ecommerce.service.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/admin/reports")
public class AdminReportController {

    private final ReportService reportService;

    public AdminReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/daily")
    public ResponseEntity<DailySalesReportResponse> getDailyReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam Long branchId) {
        return ResponseEntity.ok(reportService.getDailySalesReport(date, branchId));
    }

    @GetMapping("/summary")
    public ResponseEntity<SalesSummaryResponse> getSummaryReport() {
        return ResponseEntity.ok(reportService.getSalesSummary());
    }
}