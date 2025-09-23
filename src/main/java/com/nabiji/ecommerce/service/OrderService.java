// 📂 src/main/java/com/nabiji/ecommerce/service/OrderService.java
package com.nabiji.ecommerce.service;

import com.nabiji.ecommerce.dto.response.OrderHistoryResponse;
import com.nabiji.ecommerce.dto.response.OrderResponse;
import com.nabiji.ecommerce.security.UserPrincipal;

import java.util.List;

public interface OrderService {

    /**
     * Processes the checkout from the user's cart to create an order.
     * This is a synchronized and transactional method.
     * @param currentUser The currently logged-in user.
     * @return A DTO with the details of the created order.
     */
    OrderResponse checkout(UserPrincipal currentUser);

    /**
     * Retrieves the order history for the currently logged-in user.
     * @param currentUser The currently logged-in user.
     * @return A list of DTOs summarizing each past order.
     */
    List<OrderHistoryResponse> getOrderHistory(UserPrincipal currentUser);
}