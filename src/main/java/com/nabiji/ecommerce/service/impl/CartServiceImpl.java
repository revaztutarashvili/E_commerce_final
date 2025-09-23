package com.nabiji.ecommerce.service.impl;

import com.nabiji.ecommerce.dto.request.AddToCartRequest;
import com.nabiji.ecommerce.dto.request.UpdateCartRequest;
import com.nabiji.ecommerce.dto.response.CartItemResponse;
import com.nabiji.ecommerce.dto.response.CartResponse;
import com.nabiji.ecommerce.entity.*;
import com.nabiji.ecommerce.mapper.CartMapper;
import com.nabiji.ecommerce.repository.*;
import com.nabiji.ecommerce.security.UserPrincipal;
import com.nabiji.ecommerce.service.CartService;
import com.nabiji.ecommerce.service.InventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final ShoppingCartRepository cartRepository;
    private final UserRepository userRepository;
    private final InventoryService inventoryService;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;

    public CartServiceImpl(ShoppingCartRepository cartRepository, UserRepository userRepository, InventoryService inventoryService, CartMapper cartMapper, ProductRepository productRepository, BranchRepository branchRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.inventoryService = inventoryService;
        this.cartMapper = cartMapper;
        this.productRepository = productRepository;
        this.branchRepository = branchRepository;
    }

    @Override
    @Transactional
    public CartItemResponse addProductToCart(AddToCartRequest request, UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        Inventory inventory = inventoryService.findInventoryByBranchAndProduct(request.getBranchId(), request.getProductId());

        if (inventory.getQuantity() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        List<ShoppingCart> userCart = cartRepository.findByUserId(user.getId());
        if (!userCart.isEmpty() && !userCart.get(0).getBranch().getId().equals(request.getBranchId())) {
            cartRepository.deleteByUserId(user.getId());
            userCart.clear();
        }

        Optional<ShoppingCart> existingCartItem = userCart.stream()
                .filter(item -> item.getProduct().getId().equals(request.getProductId()))
                .findFirst();

        ShoppingCart savedItem;
        if (existingCartItem.isPresent()) {
            ShoppingCart cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
            savedItem = cartRepository.save(cartItem);
        } else {
            Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
            Branch branch = branchRepository.findById(request.getBranchId()).orElseThrow(() -> new RuntimeException("Branch not found"));
            ShoppingCart newItem = new ShoppingCart();
            newItem.setUser(user);
            newItem.setProduct(product);
            newItem.setBranch(branch);
            newItem.setQuantity(request.getQuantity());
            savedItem = cartRepository.save(newItem);
        }
        return cartMapper.toCartItemResponse(savedItem, inventory.getPrice());
    }

    @Override
    public CartResponse getCart(UserPrincipal currentUser) {
        List<ShoppingCart> cartItems = cartRepository.findByUserId(currentUser.getId());
        if (cartItems.isEmpty()) {
            return new CartResponse();
        }

        List<CartItemResponse> itemResponses = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (ShoppingCart item : cartItems) {
            Inventory inventory = inventoryService.findInventoryByBranchAndProduct(item.getBranch().getId(), item.getProduct().getId());
            CartItemResponse itemDto = cartMapper.toCartItemResponse(item, inventory.getPrice());
            itemResponses.add(itemDto);
            totalAmount = totalAmount.add(itemDto.getSubtotal());
        }

        CartResponse cartResponse = new CartResponse();
        cartResponse.setItems(itemResponses);
        cartResponse.setTotalAmount(totalAmount);
        cartResponse.setTotalItems(cartItems.size());
        cartResponse.setBranchName(cartItems.get(0).getBranch().getName());
        return cartResponse;
    }

    @Override
    @Transactional
    public CartItemResponse updateCartItem(UpdateCartRequest request, UserPrincipal currentUser) {
        ShoppingCart cartItem = cartRepository.findById(request.getCartItemId())
                .orElseThrow(() -> new RuntimeException("Cart item not found."));
        if (!Objects.equals(cartItem.getUser().getId(), currentUser.getId())) {
            throw new RuntimeException("Access denied to this cart item.");
        }
        Inventory inventory = inventoryService.findInventoryByBranchAndProduct(cartItem.getBranch().getId(), cartItem.getProduct().getId());
        if(inventory.getQuantity() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock.");
        }
        cartItem.setQuantity(request.getQuantity());
        ShoppingCart savedItem = cartRepository.save(cartItem);
        return cartMapper.toCartItemResponse(savedItem, inventory.getPrice());
    }

    @Override
    @Transactional
    public void removeCartItem(Long itemId, UserPrincipal currentUser) {
        ShoppingCart cartItem = cartRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found."));
        if (!Objects.equals(cartItem.getUser().getId(), currentUser.getId())) {
            throw new RuntimeException("Access denied to this cart item.");
        }
        cartRepository.delete(cartItem);
    }
}