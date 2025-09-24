package com.nabiji.ecommerce.repository;

import com.nabiji.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nabiji.ecommerce.enums.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<Order> findAllByBranchIdAndStatusAndCreatedAtBetween(Long branchId, OrderStatus status, LocalDateTime start, LocalDateTime end);
}