// 📂 src/test/java/com/nabiji/ecommerce/scheduler/DailySalesReportSchedulerTest.java
package com.nabiji.ecommerce.scheduler;

import com.nabiji.ecommerce.service.ReportService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DailySalesReportSchedulerTest {

    @Mock
    private ReportService reportService;

    @InjectMocks
    private DailySalesReportScheduler scheduler;

    @Test
    @DisplayName("Scheduler-მა უნდა გამოიძახოს ReportService-ის მეთოდი")
    void scheduleReportGeneration_shouldCallReportService() {
        // Arrange
        // No arrangement needed for this simple case

        // Act
        scheduler.scheduleReportGeneration();

        // Assert
        // Verify that the generateDailySalesReportForAllBranches method was called exactly once with any LocalDate
        verify(reportService, times(1)).generateDailySalesReportForAllBranches(any(LocalDate.class));
    }
}