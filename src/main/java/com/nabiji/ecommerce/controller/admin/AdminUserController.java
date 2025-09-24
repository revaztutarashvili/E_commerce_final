package com.nabiji.ecommerce.controller.admin;

import com.nabiji.ecommerce.dto.response.OrderHistoryResponse;
import com.nabiji.ecommerce.dto.response.UserProfileResponse;
import com.nabiji.ecommerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserProfileResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderHistoryResponse>> getUserOrders(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserOrderHistory(id));
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<UserProfileResponse> deactivateUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deactivateUser(id));
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<UserProfileResponse> activateUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.activateUser(id));
    }
}