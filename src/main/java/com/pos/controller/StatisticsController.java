package com.pos.controller;


import com.pos.entity.Sale;
import com.pos.service.ChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class StatisticsController{

    private final ChartService chartService;

    @GetMapping("/statistics")
    public String statistics(Model model){
        List<Map<String, Object>> Month = chartService.getMonthlyTotalPrice();
        List<Map<String, Object>> Week = chartService.getWeeklyTotalPrice();


        model.addAttribute("Month", Month);
        model.addAttribute("Week", Week);
        return "statistics";
    }
}
