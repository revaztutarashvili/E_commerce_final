

package com.nabiji.ecommerce.service;

import com.nabiji.ecommerce.entity.Inventory;
import com.nabiji.ecommerce.entity.OrderItem;
import com.nabiji.ecommerce.entity.Product;
import com.nabiji.ecommerce.exception.InsufficientStockException;
import com.nabiji.ecommerce.repository.InventoryRepository;
import com.nabiji.ecommerce.service.impl.InventoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @Test
    void decreaseStock_shouldThrowException_whenStockIsInsufficient() {
        // Arrange
        Product product = new Product();
        product.setId(1L);

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(20); // Requesting 20

        Inventory inventory = new Inventory();
        inventory.setQuantity(10); // Only 10 available

        when(inventoryRepository.findByBranchIdAndProductId(1L, 1L)).thenReturn(Optional.of(inventory));

        // Act & Assert
        assertThrows(InsufficientStockException.class, () -> {
            inventoryService.decreaseStock(List.of(orderItem), 1L);
        });
    }
}