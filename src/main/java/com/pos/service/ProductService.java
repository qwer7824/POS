package com.pos.service;

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

    public Product findById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    public Product addNewProduct(Product newProduct) {
        return productRepository.save(Product.builder()
                        .name(newProduct.getName())
                        .price(newProduct.getPrice())
                        .count(newProduct.getCount())
                .build());
    }
}
