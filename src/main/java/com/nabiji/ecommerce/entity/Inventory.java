
package com.nabiji.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"branch_id", "product_id"})
})
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

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

    @Column(nullable = false)
    @Getter @Setter
    private BigDecimal price;

    @UpdateTimestamp
    @Getter
    private LocalDateTime updatedAt;
}