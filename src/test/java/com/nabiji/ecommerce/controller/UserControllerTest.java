package com.nabiji.ecommerce.controller;

import com.nabiji.ecommerce.security.UserPrincipal;
import com.nabiji.ecommerce.service.UserService;
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
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void getUserProfile_shouldCallServiceAndReturnOk() {
        // Arrange
        UserPrincipal mockPrincipal = mock(UserPrincipal.class);

        // Act
        ResponseEntity<?> response = userController.getUserProfile(mockPrincipal);

        // Assert
        verify(userService).getUserProfile(mockPrincipal);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getCurrentBalance_shouldCallServiceAndReturnOk() {
        // Arrange
        UserPrincipal mockPrincipal = mock(UserPrincipal.class);

        // Act
        ResponseEntity<?> response = userController.getCurrentBalance(mockPrincipal);

        // Assert
        verify(userService).getCurrentBalance(mockPrincipal);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}