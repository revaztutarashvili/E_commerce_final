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

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleReportGeneration() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        logger.info("Scheduler triggered for daily report generation for date: {}", yesterday);
        reportService.generateDailySalesReportForAllBranches(yesterday);
    }
}