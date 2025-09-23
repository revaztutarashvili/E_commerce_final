package com.nabiji.ecommerce.service.impl;

import com.nabiji.ecommerce.dto.response.BalanceResponse;
import com.nabiji.ecommerce.dto.response.UserProfileResponse;
import com.nabiji.ecommerce.entity.User;
import com.nabiji.ecommerce.exception.ResourceNotFoundException;
import com.nabiji.ecommerce.mapper.UserMapper; // Import Mapper
import com.nabiji.ecommerce.repository.UserRepository;
import com.nabiji.ecommerce.security.UserPrincipal;
import com.nabiji.ecommerce.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper; // Inject Mapper

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    private User getUserFromPrincipal(UserPrincipal currentUser) {
        // This custom exception will be created in the next steps
        return userRepository.findById(currentUser.getId())
                .orElseThrow(() ->  new ResourceNotFoundException("User not found with id: " + currentUser.getId()));
    }

    @Override
    public UserProfileResponse getUserProfile(UserPrincipal currentUser) {
        User user = getUserFromPrincipal(currentUser);
        return userMapper.toUserProfileResponse(user); // Use Mapper for conversion
    }

    @Override
    public BalanceResponse getCurrentBalance(UserPrincipal currentUser) {
        User user = getUserFromPrincipal(currentUser);
        return new BalanceResponse(user.getBalance());
    }
}