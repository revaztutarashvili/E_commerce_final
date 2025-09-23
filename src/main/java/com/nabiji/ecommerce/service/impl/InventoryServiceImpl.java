package com.nabiji.ecommerce.service.impl;

import com.nabiji.ecommerce.entity.Inventory;
import com.nabiji.ecommerce.entity.OrderItem;
import com.nabiji.ecommerce.exception.InsufficientStockException;
import com.nabiji.ecommerce.exception.ResourceNotFoundException;
import com.nabiji.ecommerce.repository.InventoryRepository;
import com.nabiji.ecommerce.service.InventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Inventory findInventoryByBranchAndProduct(Long branchId, Long productId) {
        return inventoryRepository.findByBranchIdAndProductId(branchId, productId)
                .orElseThrow(() ->new ResourceNotFoundException("Product not found in this branch's inventory."));
    }

    @Override
    @Transactional
    public void decreaseStock(List<OrderItem> orderItems, Long branchId) {
        for (OrderItem item : orderItems) {
            Inventory inventory = findInventoryByBranchAndProduct(branchId, item.getProduct().getId());
            if (inventory.getQuantity() < item.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product: " + item.getProduct().getName());
            }
            inventory.setQuantity(inventory.getQuantity() - item.getQuantity());
            inventoryRepository.save(inventory);
        }
    }
}