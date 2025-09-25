package com.nabiji.ecommerce.service;

import com.nabiji.ecommerce.dto.request.AddToCartRequest;
import com.nabiji.ecommerce.entity.Inventory;
import com.nabiji.ecommerce.entity.User;
import com.nabiji.ecommerce.exception.InsufficientStockException;
import com.nabiji.ecommerce.mapper.CartMapper;
import com.nabiji.ecommerce.repository.BranchRepository;
import com.nabiji.ecommerce.repository.ProductRepository;
import com.nabiji.ecommerce.repository.ShoppingCartRepository;
import com.nabiji.ecommerce.repository.UserRepository;
import com.nabiji.ecommerce.security.UserPrincipal;
import com.nabiji.ecommerce.service.impl.CartServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import com.nabiji.ecommerce.entity.Product;


@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private ShoppingCartRepository cartRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private InventoryService inventoryService;
    @Mock
    private CartMapper cartMapper;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private CartServiceImpl cartService;
    @Test
    void addProductToCart_shouldThrowException_whenStockIsInsufficient() {
        // --- 👇 Update this test method ---
        // Arrange
        AddToCartRequest request = new AddToCartRequest();
        request.setBranchId(1L);
        request.setProductId(1L);
        request.setQuantity(15); // Requesting 15

        User user = new User();
        user.setId(1L);
        UserPrincipal principal = UserPrincipal.create(user);

        // Create a mock Product
        Product product = new Product();
        product.setName("Test Apple");

        // Create a complete mock Inventory
        Inventory inventory = new Inventory();
        inventory.setQuantity(10); // Only 10 in stock
        inventory.setProduct(product); // **THIS IS THE FIX**

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(inventoryService.findInventoryByBranchAndProduct(1L, 1L)).thenReturn(inventory);

        // Act & Assert
        assertThrows(InsufficientStockException.class, () -> {
            cartService.addProductToCart(request, principal);
        });
    }
}
