package com.nabiji.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserProfileResponse {

    private Long id;
    private String username;
    private BigDecimal balance;
    private String role;
    private boolean active;
    private LocalDateTime createdAt;
}