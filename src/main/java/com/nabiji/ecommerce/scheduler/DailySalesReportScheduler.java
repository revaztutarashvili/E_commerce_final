package com.nabiji.ecommerce.scheduler;

import com.nabiji.ecommerce.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DailySalesReportScheduler {

    private static final Logger logger = LoggerFactory.getLogger(DailySalesReportScheduler.class);
    private final ReportService reportService;

    public DailySalesReportScheduler(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * This scheduler runs every day at midnight (00:00:00).
     * It generates a sales report for the previous day for all branches.
     * The cron expression "0 0 0 * * ?" means:
     * - 0 seconds
     * - 0 minutes
     * - 0 hour (midnight)
     * - * (every day of the month)
     * - * (every month)
     * - ? (any day of the week)
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleReportGeneration() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        logger.info("Scheduler triggered for daily report generation for date: {}", yesterday);
        reportService.generateDailySalesReportForAllBranches(yesterday);
    }
}