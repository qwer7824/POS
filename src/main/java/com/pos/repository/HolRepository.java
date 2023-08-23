package com.pos.repository;

import com.pos.entity.Hol;
import com.pos.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolRepository extends JpaRepository<Hol, Integer> {

}
