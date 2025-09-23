package com.nabiji.ecommerce.controller;

import com.nabiji.ecommerce.dto.request.AddToCartRequest;
import com.nabiji.ecommerce.dto.request.UpdateCartRequest;
import com.nabiji.ecommerce.dto.response.CartItemResponse;
import com.nabiji.ecommerce.dto.response.CartResponse;
import com.nabiji.ecommerce.security.UserPrincipal;
import com.nabiji.ecommerce.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<CartItemResponse> addToCart(@Valid @RequestBody AddToCartRequest request, @AuthenticationPrincipal UserPrincipal currentUser) {
        CartItemResponse cartItem = cartService.addProductToCart(request, currentUser);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart(@AuthenticationPrincipal UserPrincipal currentUser) {
        CartResponse cart = cartService.getCart(currentUser);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/update")
    public ResponseEntity<CartItemResponse> updateCartItem(@Valid @RequestBody UpdateCartRequest request, @AuthenticationPrincipal UserPrincipal currentUser) {
        CartItemResponse updatedItem = cartService.updateCartItem(request, currentUser);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long itemId, @AuthenticationPrincipal UserPrincipal currentUser) {
        cartService.removeCartItem(itemId, currentUser);
        return ResponseEntity.noContent().build();
    }
}