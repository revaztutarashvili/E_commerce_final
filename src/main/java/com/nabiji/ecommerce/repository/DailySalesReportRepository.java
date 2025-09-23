package com.nabiji.ecommerce.repository;

import com.nabiji.ecommerce.entity.DailySalesReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailySalesReportRepository extends JpaRepository<DailySalesReport, Long> {
}