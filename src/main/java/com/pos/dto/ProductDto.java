package com.pos.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDto {
    private String name;
    private int price;
    private int count;
}
