// 📂 src/test/java/com/nabiji/ecommerce/service/PaymentServiceTest.java
package com.nabiji.ecommerce.service;

import com.nabiji.ecommerce.entity.User;
import com.nabiji.ecommerce.exception.InsufficientBalanceException;
import com.nabiji.ecommerce.repository.UserRepository;
import com.nabiji.ecommerce.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void processPayment_shouldThrowException_whenBalanceIsInsufficient() {
        // Arrange
        User user = new User();
        user.setBalance(new BigDecimal("50.00"));
        BigDecimal amountToPay = new BigDecimal("100.00");

        // Act & Assert
        assertThrows(InsufficientBalanceException.class, () -> {
            paymentService.processPayment(user, amountToPay);
        });
        verify(userRepository, never()).save(user);
    }
}