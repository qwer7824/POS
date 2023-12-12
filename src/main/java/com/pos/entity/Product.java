package com.pos.entity;


import com.pos.dto.ProductDto;
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

    public void updateCount(SaleCart saleCart) {
        this.count = count + saleCart.getCount();
    }

    public void updateProduct(ProductDto productDto){
        this.name = productDto.getName();
        this.count = productDto.getCount();
        this.price = productDto.getPrice();
    }


}
