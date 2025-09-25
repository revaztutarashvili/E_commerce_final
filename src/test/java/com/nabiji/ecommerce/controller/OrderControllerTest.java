package com.nabiji.ecommerce.controller;

import com.nabiji.ecommerce.security.UserPrincipal;
import com.nabiji.ecommerce.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    void checkout_shouldCallServiceAndReturnCreated() {
        // Arrange
        UserPrincipal principal = mock(UserPrincipal.class);

        // Act
        ResponseEntity<?> response = orderController.checkout(principal);

        // Assert
        verify(orderService).checkout(principal);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void getOrderHistory_shouldCallServiceAndReturnOk() {
        // Arrange
        UserPrincipal principal = mock(UserPrincipal.class);

        // Act
        ResponseEntity<?> response = orderController.getOrderHistory(principal);

        // Assert
        verify(orderService).getOrderHistory(principal);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}