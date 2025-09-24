package com.nabiji.ecommerce.service;

import com.nabiji.ecommerce.dto.request.AddInventoryRequest;
import com.nabiji.ecommerce.dto.request.UpdateInventoryRequest;
import com.nabiji.ecommerce.dto.response.InventoryResponse;
import com.nabiji.ecommerce.entity.Inventory;
import com.nabiji.ecommerce.entity.OrderItem;

import java.util.List;

public interface InventoryService {

    /**
     * Finds the inventory record for a specific product in a specific branch.
     * @param branchId The ID of the branch.
     * @param productId The ID of the product.
     * @return The Inventory entity.
     */
    Inventory findInventoryByBranchAndProduct(Long branchId, Long productId);

    /**
     * Decreases the stock for multiple items in an atomic operation.
     * @param orderItems The list of items to be deducted from inventory.
     * @param branchId The ID of the branch where the deduction should occur.
     */
    void decreaseStock(List<OrderItem> orderItems, Long branchId);

    InventoryResponse addInventory(AddInventoryRequest request);
    InventoryResponse updateInventory(Long branchId, Long productId, UpdateInventoryRequest request);
    void removeInventory(Long branchId, Long productId);
}