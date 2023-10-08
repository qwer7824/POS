package com.pos.controller;

import com.pos.repository.SaleRepository;
import com.pos.service.ChartService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RestChartController {
    private final ChartService chartService;

    @GetMapping("/api/month")
    public List<Map<String, Object>> getMonthlyTotalPrice() {
        return chartService.getMonthlyTotalPrice();
        }


    @GetMapping("/api/week")
    public List<Map<String, Object>> getWeeklyTotalPrice(){
        return chartService.getWeeklyTotalPrice();
        }


    }
