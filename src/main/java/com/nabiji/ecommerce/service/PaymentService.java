package com.nabiji.ecommerce.service;

import com.nabiji.ecommerce.entity.User;
import java.math.BigDecimal;

public interface PaymentService {

    /**
     * Processes a payment by deducting the amount from the user's balance.
     * @param user The user entity from whom the balance will be deducted.
     * @param totalAmount The total amount to be paid.
     * @throws com.nabiji.ecommerce.exception.InsufficientBalanceException if the user's balance is too low.
     */
    void processPayment(User user, BigDecimal totalAmount);
}