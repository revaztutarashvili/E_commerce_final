package com.nabiji.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for holding user registration data.
 */
public class UserRegistrationRequest {

    @NotBlank(message = "Username cannot be empty")
    @Getter
    @Setter
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Setter
    private String password;

    /**
     * Custom getter for the password to control access if needed,
     * following the project's minimal Lombok guidelines.
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        // Important: Do not include the password in the toString() method for security reasons.
        return "UserRegistrationRequest{" +
                "username='" + username + '\'' +
                '}';
    }
}