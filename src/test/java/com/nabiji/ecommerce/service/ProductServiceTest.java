package com.nabiji.ecommerce.service;

import com.nabiji.ecommerce.dto.response.ProductDetailsResponse;
import com.nabiji.ecommerce.entity.Branch;
import com.nabiji.ecommerce.entity.Inventory;
import com.nabiji.ecommerce.entity.Product;
import com.nabiji.ecommerce.mapper.ProductMapper;
import com.nabiji.ecommerce.repository.BranchRepository;
import com.nabiji.ecommerce.repository.InventoryRepository;
import com.nabiji.ecommerce.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void getProductsByBranch_shouldThrowException_whenBranchNotFound() {
        // Arrange
        when(branchRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            productService.getProductsByBranch(1L);
        });
    }

    @Test
    void getProductDetails_shouldReturnDetails_whenFound() {
        // --- 👇 Update this test method ---
        // Arrange
        // 1. Create complete mock objects
        Branch branch = new Branch();
        Product product = new Product();
        Inventory inventory = new Inventory();
        inventory.setBranch(branch);   // Set the nested branch object
        inventory.setProduct(product); // Set the nested product object

        // 2. Tell the repository to return your complete inventory object
        when(inventoryRepository.findByBranchIdAndProductId(1L, 1L)).thenReturn(Optional.of(inventory));

        // 3. Tell the mapper what to return when it gets any inventory object
        when(productMapper.toProductDetailsResponse(any(Inventory.class))).thenReturn(new ProductDetailsResponse());

        // Act
        ProductDetailsResponse response = productService.getProductDetails(1L, 1L);

        // Assert
        assertNotNull(response);
    }

    @Test
    void getProductDetails_shouldThrowException_whenNotFound() {
        // Arrange
        when(inventoryRepository.findByBranchIdAndProductId(1L, 1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            productService.getProductDetails(1L, 1L);
        });
    }
}