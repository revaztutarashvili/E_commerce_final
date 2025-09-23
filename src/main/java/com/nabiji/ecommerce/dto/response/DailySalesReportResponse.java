package com.nabiji.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class DailySalesReportResponse {

    private Long branchId;
    private String branchName;
    private LocalDate date;
    private BigDecimal totalSales;
    private int totalOrders;
    private List<TopProductResponse> topProducts;
}