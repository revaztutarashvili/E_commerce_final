package com.nabiji.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDetailsResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price; // Price in the specific branch
    private int availableQuantity;
    private BigDecimal basePrice;
    private String branchName;
}