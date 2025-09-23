package com.nabiji.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponse {

    private Long orderId;
    private String status;
    private BigDecimal totalAmount;
    private String branchName;
    private LocalDateTime orderDate;
    private String paymentMethod;
    private BigDecimal remainingBalance;
    private List<OrderItemResponse> items;
}