package com.nabiji.ecommerce.service;

import com.nabiji.ecommerce.entity.Branch;
import com.nabiji.ecommerce.repository.BranchRepository;
import com.nabiji.ecommerce.repository.DailySalesReportRepository;
import com.nabiji.ecommerce.repository.OrderRepository;
import com.nabiji.ecommerce.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {
    @Mock
    private DailySalesReportRepository dailySalesReportRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private ReportServiceImpl reportService;

    @Test
    void generateSingleBranchReport_shouldSaveReport() {
        // Arrange
        Branch branch = new Branch();
        branch.setId(1L);
        LocalDate date = LocalDate.now();
        when(orderRepository.findAllByBranchIdAndStatusAndCreatedAtBetween(any(), any(), any(), any()))
                .thenReturn(Collections.emptyList());

        // Act
        reportService.generateSingleBranchReport(branch, date);

        // Assert
        verify(dailySalesReportRepository, times(1)).save(any());
    }
}