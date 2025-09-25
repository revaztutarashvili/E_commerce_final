// 📂 src/test/java/com/nabiji/ecommerce/controller/admin/AdminReportControllerPureMockitoTest.java
package com.nabiji.ecommerce.controller.admin;

import com.nabiji.ecommerce.service.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AdminReportControllerPureMockitoTest {

    @Mock
    private ReportService reportService;

    @InjectMocks
    private AdminReportController adminReportController;

    @Test
    void getDailyReport_shouldCallServiceAndReturnOk() {
        // Arrange
        LocalDate date = LocalDate.of(2025, 9, 25);
        Long branchId = 1L;

        // Act
        ResponseEntity<?> response = adminReportController.getDailyReport(date, branchId);

        // Assert
        verify(reportService).getDailySalesReport(date, branchId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getSummaryReport_shouldCallServiceAndReturnOk() {
        // Arrange & Act
        ResponseEntity<?> response = adminReportController.getSummaryReport();

        // Assert
        verify(reportService).getSalesSummary();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}