package com.pos.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SaleCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hol_id")
    private Hol hol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int count;

    public void removeCount(int count){
        this.count = count - 1;
    }
    public void addCount(int count){
        this.count += count;
    }

    public static SaleCart createSaleCart(Hol hol , Product product , int count){
        SaleCart saleCart = new SaleCart();
        saleCart.setHol(hol);
        saleCart.setProduct(product);
        saleCart.setCount(count);
        return saleCart;
    }
}
