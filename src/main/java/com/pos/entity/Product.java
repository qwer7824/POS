package com.pos.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;

    private int count;

    public void removeProductCount(){
        this.count = count - 1;
    }
    public void addProductCount(){
        this.count = count + 1;
    }

    public void updateProduct(SaleCart saleCart) {
        this.count = count + saleCart.getCount();
    }}
