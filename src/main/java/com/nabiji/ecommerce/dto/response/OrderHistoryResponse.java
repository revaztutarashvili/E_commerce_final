package com.nabiji.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderHistoryResponse {

    private Long orderId;
    private String status;
    private BigDecimal totalAmount;
    private String branchName;
    private LocalDateTime orderDate;
    private int itemCount;
}