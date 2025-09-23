package com.nabiji.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TopProductResponse {

    private String productName;
    private long quantitySold;
    private BigDecimal revenue;
}