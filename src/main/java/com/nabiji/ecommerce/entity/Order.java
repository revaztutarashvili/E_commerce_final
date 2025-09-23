package com.nabiji.ecommerce.entity;

import com.nabiji.ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @Getter @Setter
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "branch_id", nullable = false)
    @Getter @Setter
    private Branch branch;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter @Setter
    private Set<OrderItem> orderItems;

    @Column(nullable = false)
    @Getter @Setter
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Getter @Setter
    private OrderStatus status;

    @Column(nullable = false)
    @Getter @Setter
    private String paymentMethod = "CARD"; // As per requirement

    @CreationTimestamp
    @Column(updatable = false)
    @Getter
    private LocalDateTime createdAt;
}