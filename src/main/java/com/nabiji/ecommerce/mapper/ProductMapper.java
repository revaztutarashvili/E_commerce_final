package com.nabiji.ecommerce.mapper;

import com.nabiji.ecommerce.dto.response.ProductDetailsResponse;
import com.nabiji.ecommerce.dto.response.ProductResponse;
import com.nabiji.ecommerce.entity.Inventory;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponse toProductResponse(Inventory inventory) {
        if (inventory == null || inventory.getProduct() == null) {
            return null;
        }

        ProductResponse response = new ProductResponse();
        response.setId(inventory.getProduct().getId());
        response.setName(inventory.getProduct().getName());
        response.setDescription(inventory.getProduct().getDescription());
        response.setPrice(inventory.getPrice());
        response.setAvailableQuantity(inventory.getQuantity());

        return response;
    }

    public ProductDetailsResponse toProductDetailsResponse(Inventory inventory) {
        if (inventory == null || inventory.getProduct() == null || inventory.getBranch() == null) {
            return null;
        }

        ProductDetailsResponse response = new ProductDetailsResponse();
        response.setId(inventory.getProduct().getId());
        response.setName(inventory.getProduct().getName());
        response.setDescription(inventory.getProduct().getDescription());
        response.setPrice(inventory.getPrice());
        response.setAvailableQuantity(inventory.getQuantity());
        response.setBasePrice(inventory.getProduct().getBasePrice());
        response.setBranchName(inventory.getBranch().getName());

        return response;
    }
}