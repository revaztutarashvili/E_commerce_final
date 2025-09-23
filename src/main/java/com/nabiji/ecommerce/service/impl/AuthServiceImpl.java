package com.nabiji.ecommerce.service.impl;

import com.nabiji.ecommerce.dto.request.LoginRequest;
import com.nabiji.ecommerce.dto.request.UserRegistrationRequest;
import com.nabiji.ecommerce.dto.response.LoginResponse;
import com.nabiji.ecommerce.entity.User;
import com.nabiji.ecommerce.enums.Role;
import com.nabiji.ecommerce.repository.UserRepository;
import com.nabiji.ecommerce.security.JwtTokenProvider;
import com.nabiji.ecommerce.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @Override
    @Transactional
    public void registerUser(UserRegistrationRequest registrationRequest) {
        // Check if username already exists
        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        // Create new user's account
        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setRole(Role.USER); // Default role
        user.setActive(true);

        userRepository.save(user);
    }

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        // Authenticate the user with Spring Security's AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Set the authentication object in the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        String jwt = tokenProvider.generateToken(authentication);

        // Get user role and expiration time
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("");

        // Let's assume you add a getter for expiration in JwtTokenProvider
        // For now, let's get it from @Value in a real scenario
        long expiresIn = 86400000; // Hardcoded for now, as defined in application.yml

        return new LoginResponse(jwt, loginRequest.getUsername(), role, expiresIn);
    }
}