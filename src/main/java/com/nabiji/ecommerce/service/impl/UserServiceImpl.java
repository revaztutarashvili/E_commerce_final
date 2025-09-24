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
import com.nabiji.ecommerce.dto.response.OrderHistoryResponse;
import com.nabiji.ecommerce.mapper.OrderMapper;
import com.nabiji.ecommerce.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, OrderRepository orderRepository, OrderMapper orderMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
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

    @Override
    public List<UserProfileResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserProfileResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderHistoryResponse> getUserOrderHistory(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(orderMapper::toOrderHistoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserProfileResponse deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        user.setActive(false);
        return userMapper.toUserProfileResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserProfileResponse activateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        user.setActive(true);
        return userMapper.toUserProfileResponse(userRepository.save(user));
    }
}