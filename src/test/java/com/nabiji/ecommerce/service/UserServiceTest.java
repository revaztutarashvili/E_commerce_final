package com.nabiji.ecommerce.service;

import com.nabiji.ecommerce.dto.response.UserProfileResponse;
import com.nabiji.ecommerce.entity.User;
import com.nabiji.ecommerce.exception.ResourceNotFoundException;
import com.nabiji.ecommerce.mapper.UserMapper;
import com.nabiji.ecommerce.repository.UserRepository;
import com.nabiji.ecommerce.security.UserPrincipal;
import com.nabiji.ecommerce.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

// ამ კლასში ვამოწმებ მომხმარებლის პროფილისა და ბალანსის ლოგიკას.
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUserProfile_shouldReturnProfile_whenUserExists() {
        // Arrange
        User user = new User();
        user.setUsername("test");
        UserPrincipal principal = UserPrincipal.create(user);
        when(userRepository.findById(principal.getId())).thenReturn(Optional.of(user));
        when(userMapper.toUserProfileResponse(user)).thenReturn(new UserProfileResponse());

        // Act
        UserProfileResponse response = userService.getUserProfile(principal);

        // Assert
        assertNotNull(response);
    }

    @Test
    void getUserProfile_shouldThrowException_whenUserNotFound() {
        // Arrange
        UserPrincipal principal = new UserPrincipal(1L, "test", "pass", true, null);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserProfile(principal);
        });
    }
}