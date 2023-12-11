package com.pos.dto;

import lombok.Getter;

@Getter
public class TopSoldProductDTO {
    private String productName;
    private Long totalSoldCount;
    private String totalSalesAmount;

    public TopSoldProductDTO(String productName, Long totalSoldCount, String totalSalesAmount) {
        this.productName = productName;
        this.totalSoldCount = totalSoldCount;
        this.totalSalesAmount = totalSalesAmount;
    }
}
