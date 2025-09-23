package com.nabiji.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "daily_sales_report")
public class DailySalesReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "branch_id", nullable = false)
    @Getter @Setter
    private Branch branch;

    @Column(nullable = false)
    @Getter @Setter
    private LocalDate date;

    @Column(nullable = false)
    @Getter @Setter
    private BigDecimal totalSales;

    @Column(nullable = false)
    @Getter @Setter
    private Integer totalOrders;

    @CreationTimestamp
    @Column(updatable = false)
    @Getter
    private LocalDateTime createdAt;
}