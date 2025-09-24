package com.nabiji.ecommerce.mapper;

import com.nabiji.ecommerce.dto.response.InventoryResponse;
import com.nabiji.ecommerce.entity.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {

    public InventoryResponse toInventoryResponse(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        InventoryResponse response = new InventoryResponse();
        response.setInventoryId(inventory.getId());
        response.setBranchName(inventory.getBranch().getName());
        response.setProductName(inventory.getProduct().getName());
        response.setQuantity(inventory.getQuantity());
        response.setPrice(inventory.getPrice());
        return response;
    }
}