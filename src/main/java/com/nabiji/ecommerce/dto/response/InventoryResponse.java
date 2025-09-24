package com.nabiji.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class InventoryResponse {
    private Long inventoryId;
    private String branchName;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
}