package com.pos.repository;

import com.pos.entity.SaleCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleCartRepository extends JpaRepository<SaleCart, Long> {

    List<SaleCart> findByHolId(int hid);

    SaleCart findByHolIdAndProductId(int hid, Long pid);

    void deleteByHolIdAndProductId(int hid, Long pid);

    void deleteByHolId(int hid);


}
