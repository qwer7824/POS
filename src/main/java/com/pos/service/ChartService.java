package com.pos.service;

import com.pos.dto.TopSoldProductDTO;
import com.pos.entity.Product;
import com.pos.repository.SaleDetailRepository;
import com.pos.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChartService {

    private final SaleRepository saleRepository;
    private final SaleDetailRepository saleDetailRepository;

    public List<Map<String, Object>> getMonthlyTotalPrice() {
        return saleRepository.getMonthlyTotalPrice();
    }

    public List<Map<String, Object>> getWeeklyTotalPrice() {

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusWeeks(1).plusDays(1);

        List<Map<String, Object>> dailyTotalPrice = saleRepository.getDailyTotalPrice(startDate, endDate);
        return dailyTotalPrice;
    }

    public List<TopSoldProductDTO> getTopSoldProducts() {
        LocalDate endDate = LocalDate.now(); // 현재 날짜
        LocalDate startDate = endDate.minusMonths(1); // 한 달 전 날짜

        List<Object[]> topSoldProducts = saleDetailRepository.findTopSoldProducts(startDate, endDate);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        List<TopSoldProductDTO> sortedProducts = new ArrayList<>();
        for (Object[] obj : topSoldProducts) {
            Product product = (Product) obj[0];
            Long totalSoldCount = (Long) obj[1];
            Long totalSalesAmount = (Long) obj[2];

            String productName = product.getName(); // 상품의 제목을 가져옵니다.
            String formattedSalesAmount = decimalFormat.format(totalSalesAmount);

            TopSoldProductDTO dto = new TopSoldProductDTO(productName, totalSoldCount, formattedSalesAmount);
            sortedProducts.add(dto);
        }

        return sortedProducts;
    }

}