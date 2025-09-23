package com.nabiji.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class SalesSummaryResponse {

    private BigDecimal totalRevenueAllBranches;
    private long totalOrdersAllBranches;
    private LocalDate reportStartDate;
    private LocalDate reportEndDate;
}