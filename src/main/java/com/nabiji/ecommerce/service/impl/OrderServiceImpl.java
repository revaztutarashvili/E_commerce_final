package com.nabiji.ecommerce.service.impl;

import com.nabiji.ecommerce.aspect.LogExecutionTime;
import com.nabiji.ecommerce.dto.response.OrderHistoryResponse;
import com.nabiji.ecommerce.dto.response.OrderResponse;
import com.nabiji.ecommerce.entity.*;
import com.nabiji.ecommerce.enums.OrderStatus;
import com.nabiji.ecommerce.exception.CartEmptyException;
import com.nabiji.ecommerce.mapper.OrderMapper; // Import Mapper
import com.nabiji.ecommerce.repository.*;
import com.nabiji.ecommerce.security.UserPrincipal;
import com.nabiji.ecommerce.service.InventoryService;
import com.nabiji.ecommerce.service.OrderService;
import com.nabiji.ecommerce.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ShoppingCartRepository cartRepository;
    private final UserRepository userRepository;
    private final PaymentService paymentService;
    private final InventoryService inventoryService;
    private final OrderMapper orderMapper; // Inject Mapper

    public OrderServiceImpl(OrderRepository orderRepository, /* ... other repos ... */ PaymentService paymentService, InventoryService inventoryService, OrderMapper orderMapper, UserRepository userRepository, ShoppingCartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
        this.orderMapper = orderMapper;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional
    @LogExecutionTime
    public synchronized OrderResponse checkout(UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new RuntimeException("User not found."));
        List<ShoppingCart> cartItems = cartRepository.findByUserId(user.getId());

        if (cartItems.isEmpty()) {
            throw new CartEmptyException("Cart is empty. Cannot proceed to checkout.");

        }

        Branch branch = cartItems.get(0).getBranch();
        BigDecimal totalAmount = calculateTotalAmount(cartItems);

        Order order = createOrder(user, branch, totalAmount);

        Set<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> createOrderItem(cartItem, order))
                .collect(Collectors.toSet());
        order.setOrderItems(orderItems);

        paymentService.processPayment(user, totalAmount);
        inventoryService.decreaseStock(List.copyOf(orderItems), branch.getId());

        order.setStatus(OrderStatus.COMPLETED);
        Order savedOrder = orderRepository.save(order);
        cartRepository.deleteByUserId(user.getId());

        return orderMapper.toOrderResponse(savedOrder); // Use Mapper for conversion
    }

    @Override
    public List<OrderHistoryResponse> getOrderHistory(UserPrincipal currentUser) {
        List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(currentUser.getId());
        return orders.stream()
                .map(orderMapper::toOrderHistoryResponse) // Use Mapper for conversion
                .collect(Collectors.toList());
    }

    private BigDecimal calculateTotalAmount(List<ShoppingCart> cartItems) {
        BigDecimal total = BigDecimal.ZERO;
        for (ShoppingCart item : cartItems) {
            Inventory inventory = inventoryService.findInventoryByBranchAndProduct(item.getBranch().getId(), item.getProduct().getId());
            total = total.add(inventory.getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        return total;
    }

    private Order createOrder(User user, Branch branch, BigDecimal totalAmount) {
        Order order = new Order();
        order.setUser(user);
        order.setBranch(branch);
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(totalAmount);
        return order;
    }

    private OrderItem createOrderItem(ShoppingCart cartItem, Order order) {
        Inventory inventory = inventoryService.findInventoryByBranchAndProduct(cartItem.getBranch().getId(), cartItem.getProduct().getId());
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(cartItem.getProduct());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setUnitPrice(inventory.getPrice());
        orderItem.setSubtotal(inventory.getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
        orderItem.setOrder(order);
        return orderItem;
    }
}