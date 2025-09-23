package com.nabiji.ecommerce.mapper;

import com.nabiji.ecommerce.dto.response.UserProfileResponse;
import com.nabiji.ecommerce.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserProfileResponse toUserProfileResponse(User user) {
        if (user == null) {
            return null;
        }

        UserProfileResponse response = new UserProfileResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setBalance(user.getBalance());
        response.setRole(user.getRole().name());
        response.setActive(user.isActive());
        response.setCreatedAt(user.getCreatedAt());

        return response;
    }
}