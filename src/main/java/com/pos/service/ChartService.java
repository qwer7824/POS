package com.pos.service;

import com.pos.entity.Hol;
import com.pos.entity.SaleCart;
import com.pos.repository.HolRepository;
import com.pos.repository.SaleCartRepository;
import com.pos.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChartService {

    private final SaleRepository saleRepository;

    public List<Map<String, Object>> getMonthlyTotalPrice() {
        return saleRepository.getMonthlyTotalPrice();
    }

    public List<Map<String, Object>> getWeeklyTotalPrice() {

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusWeeks(1).plusDays(1);

        List<Map<String, Object>> dailyTotalPrice = saleRepository.getDailyTotalPrice(startDate, endDate);
        return dailyTotalPrice;
    }


}