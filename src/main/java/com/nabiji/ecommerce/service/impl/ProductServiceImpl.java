package com.nabiji.ecommerce.service.impl;

import com.nabiji.ecommerce.dto.response.ProductDetailsResponse;
import com.nabiji.ecommerce.dto.response.ProductResponse;
import com.nabiji.ecommerce.entity.Inventory;
import com.nabiji.ecommerce.mapper.ProductMapper; // Import Mapper
import com.nabiji.ecommerce.repository.BranchRepository;
import com.nabiji.ecommerce.repository.InventoryRepository;
import com.nabiji.ecommerce.service.ProductService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final InventoryRepository inventoryRepository;
    private final BranchRepository branchRepository;
    private final ProductMapper productMapper; // Inject Mapper

    public ProductServiceImpl(InventoryRepository inventoryRepository, BranchRepository branchRepository, ProductMapper productMapper) {
        this.inventoryRepository = inventoryRepository;
        this.branchRepository = branchRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductResponse> getProductsByBranch(Long branchId) {
        if (!branchRepository.existsById(branchId)) {
            throw new RuntimeException("Branch not found with id: " + branchId);
        }
        List<Inventory> inventoryList = inventoryRepository.findByBranchId(branchId);
        return inventoryList.stream()
                .map(productMapper::toProductResponse) // Use Mapper for conversion
                .collect(Collectors.toList());
    }

    @Override
    public ProductDetailsResponse getProductDetails(Long productId, Long branchId) {
        Inventory inventory = inventoryRepository.findByBranchIdAndProductId(branchId, productId)
                .orElseThrow(() -> new RuntimeException("Product not found in this branch."));

        return productMapper.toProductDetailsResponse(inventory); // Use Mapper for conversion
    }
}