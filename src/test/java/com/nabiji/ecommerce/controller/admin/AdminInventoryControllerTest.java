package com.nabiji.ecommerce.controller.admin;

import com.nabiji.ecommerce.dto.request.AddInventoryRequest;
import com.nabiji.ecommerce.dto.request.UpdateInventoryRequest;
import com.nabiji.ecommerce.dto.response.InventoryResponse;
import com.nabiji.ecommerce.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminInventoryControllerPureMockitoTest {

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private AdminInventoryController adminInventoryController;

    @Test
    void addInventory_shouldCallServiceAndReturnCreated() {
        // Arrange
        AddInventoryRequest request = new AddInventoryRequest();
        request.setBranchId(1L);
        request.setProductId(1L);
        request.setQuantity(100);

        when(inventoryService.addInventory(request)).thenReturn(new InventoryResponse());

        // Act
        ResponseEntity<InventoryResponse> response = adminInventoryController.addInventory(request);

        // Assert
        verify(inventoryService).addInventory(request);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void updateInventory_shouldCallServiceAndReturnOk() {
        // Arrange
        Long branchId = 1L;
        Long productId = 1L;
        UpdateInventoryRequest request = new UpdateInventoryRequest();
        request.setQuantity(50);

        when(inventoryService.updateInventory(branchId, productId, request)).thenReturn(new InventoryResponse());

        // Act
        ResponseEntity<InventoryResponse> response = adminInventoryController.updateInventory(branchId, productId, request);

        // Assert
        verify(inventoryService).updateInventory(branchId, productId, request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void removeInventory_shouldCallServiceAndReturnNoContent() {
        // Arrange
        Long branchId = 1L;
        Long productId = 1L;

        // Act
        ResponseEntity<Void> response = adminInventoryController.removeInventory(branchId, productId);

        // Assert
        verify(inventoryService).removeInventory(branchId, productId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}