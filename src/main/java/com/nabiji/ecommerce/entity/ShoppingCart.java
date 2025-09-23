package com.nabiji.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @Getter @Setter
    private Product product;

    @Column(nullable = false)
    @Getter @Setter
    private Integer quantity;

    @CreationTimestamp
    @Column(updatable = false)
    @Getter
    private LocalDateTime createdAt;
}