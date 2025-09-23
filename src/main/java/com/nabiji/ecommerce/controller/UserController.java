package com.nabiji.ecommerce.controller;

import com.nabiji.ecommerce.dto.response.BalanceResponse;
import com.nabiji.ecommerce.dto.response.UserProfileResponse;
import com.nabiji.ecommerce.security.UserPrincipal;
import com.nabiji.ecommerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint to get the profile of the currently logged-in user.
     * @param currentUser The authenticated user principal injected by Spring Security.
     * @return ResponseEntity with the user's profile.
     */
    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getUserProfile(@AuthenticationPrincipal UserPrincipal currentUser) {
        UserProfileResponse userProfile = userService.getUserProfile(currentUser);
        return ResponseEntity.ok(userProfile);
    }

    /**
     * Endpoint to get the current balance of the logged-in user.
     * @param currentUser The authenticated user principal.
     * @return ResponseEntity with the user's balance.
     */
    @GetMapping("/balance")
    public ResponseEntity<BalanceResponse> getCurrentBalance(@AuthenticationPrincipal UserPrincipal currentUser) {
        BalanceResponse balance = userService.getCurrentBalance(currentUser);
        return ResponseEntity.ok(balance);
    }
}