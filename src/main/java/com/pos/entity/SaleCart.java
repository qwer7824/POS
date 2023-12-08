package com.pos.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SaleCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hol_id")
    private Hol hol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int count;

    public void removeCount(int count){
        this.count = count - 1;
    }
    public void addCount(int count) {
            this.count = count + 1;
    }

    public void createSaleCart(Hol hol , Product product){
            this.hol = hol;
            this.product = product;
            this.count = 1;
    }
}
