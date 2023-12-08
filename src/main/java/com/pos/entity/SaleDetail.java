package com.pos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
public class SaleDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int price;
    private int count;


    public SaleDetail(Sale sale, Product product, int price, int count) {
        this.sale = sale; // sid 속성 설정
        this.product = product; // product 속성 설정
        this.price = price; // price 속성 설정
        this.count = count; // count 속성 설정
    }
}
