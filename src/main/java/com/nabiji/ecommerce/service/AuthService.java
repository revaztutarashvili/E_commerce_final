// 📂 src/main/java/com/nabiji/ecommerce/service/AuthService.java
package com.nabiji.ecommerce.service;

import com.nabiji.ecommerce.dto.request.LoginRequest;
import com.nabiji.ecommerce.dto.request.UserRegistrationRequest;
import com.nabiji.ecommerce.dto.response.LoginResponse;

public interface AuthService {

    /**
     * Registers a new user in the system.
     * @param registrationRequest DTO containing user registration details.
     * @throws RuntimeException if the username is already taken.
     */
    void registerUser(UserRegistrationRequest registrationRequest);

    /**
     * Authenticates a user and returns a JWT token.
     * @param loginRequest DTO containing login credentials.
     * @return LoginResponse DTO with the JWT token and user details.
     */
    LoginResponse loginUser(LoginRequest loginRequest);
}