package com.nabiji.ecommerce.controller;

import com.nabiji.ecommerce.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void getProductsInBranch_shouldCallServiceAndReturnOk() {
        // Arrange
        Long branchId = 1L;

        // Act
        ResponseEntity<?> response = productController.getProductsInBranch(branchId);

        // Assert
        verify(productService).getProductsByBranch(branchId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getProductDetails_shouldCallServiceAndReturnOk() {
        // Arrange
        Long productId = 1L;
        Long branchId = 1L;

        // Act
        ResponseEntity<?> response = productController.getProductDetails(productId, branchId);

        // Assert
        verify(productService).getProductDetails(productId, branchId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}