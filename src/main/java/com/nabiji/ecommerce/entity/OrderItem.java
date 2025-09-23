package com.nabiji.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @Getter @Setter
    private Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @Getter @Setter
    private Product product;

    @Column(nullable = false)
    @Getter @Setter
    private Integer quantity;

    @Column(nullable = false)
    @Getter @Setter
    private BigDecimal unitPrice;

    @Column(nullable = false)
    @Getter @Setter
    private BigDecimal subtotal;
}