package com.nabiji.ecommerce.service;

import com.nabiji.ecommerce.dto.request.UserRegistrationRequest;
import com.nabiji.ecommerce.entity.User;
import com.nabiji.ecommerce.repository.UserRepository;
import com.nabiji.ecommerce.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// ამ ტესტრში ვამოწმებ რეგისტრაციისა და ავტორიზაციის ლოგიკას.

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    @DisplayName("მომხმარებელი უნდა დარეგისტრირდეს წარმატებით")
    void registerUser_shouldSucceed_whenUsernameIsUnique() {
        // Arrange
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("testuser");
        request.setPassword("password123");

        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");

        // Act
        authService.registerUser(request);

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("რეგისტრაცია უნდა დასრულდეს შეცდომით, თუ მომხმარებლის სახელი დაკავებულია")
    void registerUser_shouldThrowException_whenUsernameExists() {
        // Arrange
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("existinguser");
        request.setPassword("password123");

        when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            authService.registerUser(request);
        });

        verify(userRepository, never()).save(any(User.class));
    }
}