package com.nabiji.ecommerce.controller;

import com.nabiji.ecommerce.dto.response.OrderHistoryResponse;
import com.nabiji.ecommerce.dto.response.OrderResponse;
import com.nabiji.ecommerce.security.UserPrincipal;
import com.nabiji.ecommerce.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Endpoint to create an order from the user's current cart.
     * @param currentUser The authenticated user principal.
     * @return ResponseEntity with the details of the created order.
     */
    @PostMapping("/checkout")
    public ResponseEntity<OrderResponse> checkout(@AuthenticationPrincipal UserPrincipal currentUser) {
        OrderResponse order = orderService.checkout(currentUser);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    /**
     * Endpoint to view the order history for the logged-in user.
     * @param currentUser The authenticated user principal.
     * @return ResponseEntity with a list of past orders.
     */
    @GetMapping("/history")
    public ResponseEntity<List<OrderHistoryResponse>> getOrderHistory(@AuthenticationPrincipal UserPrincipal currentUser) {
        List<OrderHistoryResponse> history = orderService.getOrderHistory(currentUser);
        return ResponseEntity.ok(history);
    }
}