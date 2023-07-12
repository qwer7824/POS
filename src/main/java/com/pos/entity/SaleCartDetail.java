package com.pos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SaleCartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hid;
    private Long pid;
    private String name;
    private int price;
    private int count;
}
