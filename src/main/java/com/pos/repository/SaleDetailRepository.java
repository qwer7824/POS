package com.pos.repository;

import com.pos.entity.SaleCart;
import com.pos.entity.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long> {
    List<SaleDetail> findBySaleId(Long sid);


    @Query("SELECT sd.product, SUM(sd.count) AS totalSoldCount, SUM(sd.price * sd.count) AS totalSalesAmount " +
            "FROM SaleDetail sd " +
            "WHERE sd.sale.createdDate BETWEEN :startDate AND :endDate " +
            "GROUP BY sd.product " +
            "ORDER BY totalSoldCount DESC")
    List<Object[]> findTopSoldProducts(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
