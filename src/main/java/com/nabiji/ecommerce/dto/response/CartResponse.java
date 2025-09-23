package com.nabiji.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CartResponse {

    private List<CartItemResponse> items;
    private BigDecimal totalAmount;
    private int totalItems;
    private String branchName;
}