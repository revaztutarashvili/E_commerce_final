package com.nabiji.ecommerce.controller;

import com.nabiji.ecommerce.dto.request.AddToCartRequest;
import com.nabiji.ecommerce.dto.request.UpdateCartRequest;
import com.nabiji.ecommerce.security.UserPrincipal;
import com.nabiji.ecommerce.service.CartService;
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
class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @Test
    void addToCart_shouldCallServiceAndReturnCreated() {
        // Arrange
        AddToCartRequest request = new AddToCartRequest();
        UserPrincipal principal = mock(UserPrincipal.class);

        // Act
        ResponseEntity<?> response = cartController.addToCart(request, principal);

        // Assert
        verify(cartService).addProductToCart(request, principal);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void getCart_shouldCallServiceAndReturnOk() {
        // Arrange
        UserPrincipal principal = mock(UserPrincipal.class);

        // Act
        ResponseEntity<?> response = cartController.getCart(principal);

        // Assert
        verify(cartService).getCart(principal);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateCartItem_shouldCallServiceAndReturnOk() {
        // Arrange
        UpdateCartRequest request = new UpdateCartRequest();
        UserPrincipal principal = mock(UserPrincipal.class);

        // Act
        ResponseEntity<?> response = cartController.updateCartItem(request, principal);

        // Assert
        verify(cartService).updateCartItem(request, principal);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void removeFromCart_shouldCallServiceAndReturnNoContent() {
        // Arrange
        Long itemId = 1L;
        UserPrincipal principal = mock(UserPrincipal.class);

        // Act
        ResponseEntity<?> response = cartController.removeFromCart(itemId, principal);

        // Assert
        verify(cartService).removeCartItem(itemId, principal);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}