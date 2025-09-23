package com.nabiji.ecommerce.mapper;

import com.nabiji.ecommerce.dto.response.OrderHistoryResponse;
import com.nabiji.ecommerce.dto.response.OrderItemResponse;
import com.nabiji.ecommerce.dto.response.OrderResponse;
import com.nabiji.ecommerce.entity.Order;
import com.nabiji.ecommerce.entity.OrderItem;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderResponse toOrderResponse(Order order) {
        if (order == null) {
            return null;
        }

        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId());
        response.setStatus(order.getStatus().name());
        response.setTotalAmount(order.getTotalAmount());
        response.setBranchName(order.getBranch().getName());
        response.setOrderDate(order.getCreatedAt());
        response.setPaymentMethod(order.getPaymentMethod());
        response.setRemainingBalance(order.getUser().getBalance());

        List<OrderItemResponse> itemResponses = order.getOrderItems().stream()
                .map(this::toOrderItemResponse)
                .collect(Collectors.toList());
        response.setItems(itemResponses);

        return response;
    }

    public OrderHistoryResponse toOrderHistoryResponse(Order order) {
        if (order == null) {
            return null;
        }

        OrderHistoryResponse response = new OrderHistoryResponse();
        response.setOrderId(order.getId());
        response.setStatus(order.getStatus().name());
        response.setTotalAmount(order.getTotalAmount());
        response.setOrderDate(order.getCreatedAt());
        response.setBranchName(order.getBranch().getName());
        response.setItemCount(order.getOrderItems().size());

        return response;
    }

    private OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }

        OrderItemResponse response = new OrderItemResponse();
        response.setProductName(orderItem.getProduct().getName());
        response.setQuantity(orderItem.getQuantity());
        response.setUnitPrice(orderItem.getUnitPrice());
        response.setSubtotal(orderItem.getSubtotal());

        return response;
    }
}