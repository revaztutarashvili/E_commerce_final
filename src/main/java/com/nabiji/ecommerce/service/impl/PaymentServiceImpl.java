package com.nabiji.ecommerce.service.impl;

import com.nabiji.ecommerce.entity.User;
import com.nabiji.ecommerce.exception.InsufficientBalanceException;
import com.nabiji.ecommerce.repository.UserRepository;
import com.nabiji.ecommerce.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final UserRepository userRepository;

    public PaymentServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void processPayment(User user, BigDecimal totalAmount) {
        if (user.getBalance().compareTo(totalAmount) < 0) {
            // This custom exception will be created in the next step
            throw new InsufficientBalanceException("Insufficient balance for this transaction.");
        }
        BigDecimal newBalance = user.getBalance().subtract(totalAmount);
        user.setBalance(newBalance);
        userRepository.save(user);
    }
}