package com.nabiji.ecommerce.service;

import com.nabiji.ecommerce.entity.*;
import com.nabiji.ecommerce.exception.CartEmptyException;
import com.nabiji.ecommerce.exception.InsufficientBalanceException;
import com.nabiji.ecommerce.exception.InsufficientStockException;
import com.nabiji.ecommerce.repository.OrderRepository;
import com.nabiji.ecommerce.repository.ShoppingCartRepository;
import com.nabiji.ecommerce.repository.UserRepository;
import com.nabiji.ecommerce.security.UserPrincipal;
import com.nabiji.ecommerce.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ShoppingCartRepository cartRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PaymentService paymentService;
    @Mock
    private InventoryService inventoryService;
    @Mock
    private com.nabiji.ecommerce.mapper.OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private User user;
    private UserPrincipal principal;
    private ShoppingCart cartItem;
    private Branch branch; // <- Add Branch
    private Product product;


    @BeforeEach
    void setUp() {
        // This setup is now correct and complete
        user = new User();
        user.setId(1L);
        principal = UserPrincipal.create(user);
        branch = new Branch();
        branch.setId(1L);
        product = new Product();
        product.setId(1L);
        cartItem = new ShoppingCart();
        cartItem.setBranch(branch);
        cartItem.setProduct(product);
        cartItem.setQuantity(5);
        cartItem.setUser(user);
    }

    @Test
    void checkout_shouldThrowException_whenCartIsEmpty() {
        // This test is correct and should pass
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(1L)).thenReturn(Collections.emptyList());
        assertThrows(CartEmptyException.class, () -> orderService.checkout(principal));
    }

    @Test
    void checkout_shouldThrowException_whenBalanceIsInsufficient() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.setPrice(new BigDecimal("10.00")); // Give the inventory a price

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(1L)).thenReturn(List.of(cartItem));
        // 👇 **THIS IS THE FIX** 👇
        // Tell Mockito what to return when the inventory is requested
        when(inventoryService.findInventoryByBranchAndProduct(1L, 1L)).thenReturn(inventory);

        doThrow(new InsufficientBalanceException("")).when(paymentService).processPayment(any(), any());

        // Act & Assert
        assertThrows(InsufficientBalanceException.class, () -> orderService.checkout(principal));
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void checkout_shouldThrowException_whenStockIsInsufficient() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.setPrice(new BigDecimal("10.00")); // Give the inventory a price

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(1L)).thenReturn(List.of(cartItem));
        // 👇 **THIS IS THE FIX** 👇
        // Tell Mockito what to return when the inventory is requested
        when(inventoryService.findInventoryByBranchAndProduct(1L, 1L)).thenReturn(inventory);

        doThrow(new InsufficientStockException("")).when(inventoryService).decreaseStock(any(), any());

        // Act & Assert
        assertThrows(InsufficientStockException.class, () -> orderService.checkout(principal));
        verify(orderRepository, never()).save(any(Order.class));
    }


}