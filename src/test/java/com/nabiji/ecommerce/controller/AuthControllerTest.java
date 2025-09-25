package com.nabiji.ecommerce.controller;

import com.nabiji.ecommerce.dto.request.LoginRequest;
import com.nabiji.ecommerce.dto.request.UserRegistrationRequest;
import com.nabiji.ecommerce.dto.response.LoginResponse;
import com.nabiji.ecommerce.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    @DisplayName("რეგისტრაციამ უნდა გამოიძახოს სერვისი და დააბრუნოს CREATED სტატუსი")
    void registerUser_shouldCallServiceAndReturnCreated() {
        // Arrange
        UserRegistrationRequest request = new UserRegistrationRequest();

        // Act
        ResponseEntity<String> response = authController.registerUser(request);

        // Assert
        verify(authService).registerUser(request);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User registered successfully!", response.getBody());
    }

    @Test
    @DisplayName("ავტორიზაციამ უნდა გამოიძახოს სერვისი და დააბრუნოს OK სტატუსი ტოკენთან ერთად")
    void authenticateUser_shouldCallServiceAndReturnToken() {
        // Arrange
        LoginRequest request = new LoginRequest();
        LoginResponse loginResponse = new LoginResponse("token", "user", "ROLE_USER", 3600L);
        when(authService.loginUser(request)).thenReturn(loginResponse);

        // Act
        ResponseEntity<LoginResponse> response = authController.authenticateUser(request);

        // Assert
        verify(authService).loginUser(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("token", response.getBody().getToken());
    }
}