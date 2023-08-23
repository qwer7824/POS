package com.pos.repository;

import com.pos.entity.SaleCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleCartRepository extends JpaRepository<SaleCart, Long> {

    List<SaleCart> findByHid(int hid);

    SaleCart findByHidAndPid(int hid, Long pid);

    void deleteByHidAndPid(int hid, Long pid);

    void deleteByHid(int hid);

}
