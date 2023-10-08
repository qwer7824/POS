package com.pos.repository;

import com.pos.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findAllByCreatedDateBetween(LocalDate start, LocalDate end);

    @Query("SELECT MONTH(s.createdDate) AS month, SUM(s.price) AS totalPrice, COUNT(s) AS totalOrderCount FROM Sale s GROUP BY MONTH(s.createdDate)")
    List<Map<String, Object>> getMonthlyTotalPrice();

    @Query("SELECT DATE(s.createdDate) AS day, SUM(s.price) AS totalPrice, COUNT(s) AS totalOrderCount FROM Sale s WHERE s.createdDate >= :startDate AND s.createdDate <= :endDate GROUP BY DATE(s.createdDate) ORDER BY DATE(s.createdDate)")
    List<Map<String, Object>> getDailyTotalPrice(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


}
