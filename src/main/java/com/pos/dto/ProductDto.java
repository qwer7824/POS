package com.pos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDto {
    @NotBlank
    private String name;
    @Min(1)
    private int price;
    @Min(1)
    private int count;
}
