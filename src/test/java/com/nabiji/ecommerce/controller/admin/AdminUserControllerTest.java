// 📂 src/test/java/com/nabiji/ecommerce/controller/admin/AdminUserControllerPureMockitoTest.java
package com.nabiji.ecommerce.controller.admin;

import com.nabiji.ecommerce.service.UserService;
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
class AdminUserControllerPureMockitoTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AdminUserController adminUserController;

    @Test
    void getAllUsers_shouldCallServiceAndReturnOk() {
        // Arrange & Act
        ResponseEntity<?> response = adminUserController.getAllUsers();

        // Assert
        verify(userService).getAllUsers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getUserOrders_shouldCallServiceAndReturnOk() {
        // Arrange
        Long userId = 1L;

        // Act
        ResponseEntity<?> response = adminUserController.getUserOrders(userId);

        // Assert
        verify(userService).getUserOrderHistory(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deactivateUser_shouldCallServiceAndReturnOk() {
        // Arrange
        Long userId = 1L;

        // Act
        ResponseEntity<?> response = adminUserController.deactivateUser(userId);

        // Assert
        verify(userService).deactivateUser(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void activateUser_shouldCallServiceAndReturnOk() {
        // Arrange
        Long userId = 1L;

        // Act
        ResponseEntity<?> response = adminUserController.activateUser(userId);

        // Assert
        verify(userService).activateUser(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}