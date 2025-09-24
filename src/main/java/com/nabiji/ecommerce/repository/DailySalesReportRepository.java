package com.nabiji.ecommerce.repository;

import com.nabiji.ecommerce.entity.DailySalesReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;
@Repository
public interface DailySalesReportRepository extends JpaRepository<DailySalesReport, Long> {

    Optional<DailySalesReport> findByDateAndBranchId(LocalDate date, Long branchId);

}