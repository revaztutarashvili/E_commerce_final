package com.nabiji.ecommerce.mapper;

import com.nabiji.ecommerce.dto.response.CartItemResponse;
import com.nabiji.ecommerce.dto.response.CartResponse;
import com.nabiji.ecommerce.entity.ShoppingCart;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartItemResponse toCartItemResponse(ShoppingCart cartItem, BigDecimal unitPrice) {
        if (cartItem == null || cartItem.getProduct() == null) {
            return null;
        }

        CartItemResponse response = new CartItemResponse();
        response.setId(cartItem.getId());
        response.setProductId(cartItem.getProduct().getId());
        response.setProductName(cartItem.getProduct().getName());
        response.setQuantity(cartItem.getQuantity());
        response.setUnitPrice(unitPrice);
        response.setSubtotal(unitPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity())));

        return response;
    }
}