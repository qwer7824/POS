package com.pos.service;

import com.pos.dto.ProductDto;
import com.pos.entity.Product;
import com.pos.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> productList(){
        return productRepository.findAll();
    }

    public Product addNewProduct(ProductDto newProduct) {
        return productRepository.save(Product.builder()
                        .name(newProduct.getName())
                        .price(newProduct.getPrice())
                        .count(newProduct.getCount())
                .build());
    }
}
