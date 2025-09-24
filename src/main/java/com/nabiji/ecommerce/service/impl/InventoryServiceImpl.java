package com.nabiji.ecommerce.service.impl;

import com.nabiji.ecommerce.dto.request.UpdateInventoryRequest;
import com.nabiji.ecommerce.dto.response.InventoryResponse;
import com.nabiji.ecommerce.entity.Branch;
import com.nabiji.ecommerce.entity.Inventory;
import com.nabiji.ecommerce.entity.OrderItem;
import com.nabiji.ecommerce.entity.Product;
import com.nabiji.ecommerce.exception.InsufficientStockException;
import com.nabiji.ecommerce.exception.ResourceNotFoundException;
import com.nabiji.ecommerce.mapper.InventoryMapper;
import com.nabiji.ecommerce.repository.BranchRepository;
import com.nabiji.ecommerce.repository.InventoryRepository;
import com.nabiji.ecommerce.repository.ProductRepository;
import com.nabiji.ecommerce.service.InventoryService;
import com.nabiji.ecommerce.dto.request.AddInventoryRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;
    private final InventoryMapper inventoryMapper;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, ProductRepository productRepository, BranchRepository branchRepository, InventoryMapper inventoryMapper) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.branchRepository = branchRepository;
        this.inventoryMapper = inventoryMapper;
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

    @Override
    @Transactional
    public InventoryResponse addInventory(AddInventoryRequest request) {
        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // If inventory exists, update it. Otherwise, create new.
        Inventory inventory = inventoryRepository.findByBranchIdAndProductId(request.getBranchId(), request.getProductId())
                .orElse(new Inventory());

        inventory.setBranch(branch);
        inventory.setProduct(product);
        inventory.setQuantity(request.getQuantity());

        // Calculate price based on branch markup logic (assuming base price of 1)
        BigDecimal basePrice = product.getBasePrice();
        BigDecimal price = basePrice.add(new BigDecimal(branch.getId())); // Simplified logic from spec
        inventory.setPrice(price);

        Inventory savedInventory = inventoryRepository.save(inventory);
        return inventoryMapper.toInventoryResponse(savedInventory);
    }

    @Override
    @Transactional
    public InventoryResponse updateInventory(Long inventoryId, UpdateInventoryRequest request) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory record not found"));
        inventory.setQuantity(request.getQuantity());
        Inventory savedInventory = inventoryRepository.save(inventory);
        return inventoryMapper.toInventoryResponse(savedInventory);
    }

    @Override
    @Transactional
    public void removeInventory(Long inventoryId) {
        if (!inventoryRepository.existsById(inventoryId)) {
            throw new ResourceNotFoundException("Inventory record not found");
        }
        inventoryRepository.deleteById(inventoryId);
    }
}