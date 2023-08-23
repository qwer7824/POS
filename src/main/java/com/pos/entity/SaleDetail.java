package com.pos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class SaleDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sid;
    private Long pid;
    private String name;
    private int price;
    private int count;

    public SaleDetail(Long sid, Long pid, String name, int price, int count) {
        this.sid = sid;
        this.pid = pid;
        this.name = name;
        this.price = price;
        this.count = count;
    }
}
