package com.nabiji.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for holding user login credentials.
 */
public class LoginRequest {

    @NotBlank(message = "Username cannot be empty")
    @Getter
    @Setter
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Getter
    @Setter
    private String password;
}