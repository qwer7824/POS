package com.pos.controller;


import com.pos.dto.TopSoldProductDTO;
import com.pos.entity.Product;
import com.pos.service.ChartService;
import com.pos.service.ProductService;
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
    private final ProductService productService;

    @GetMapping("/statistics")
    public String statistics(Model model){
        List<Map<String, Object>> Month = chartService.getMonthlyTotalPrice();
        List<Map<String, Object>> Week = chartService.getWeeklyTotalPrice();
        List<Product> Products = productService.productList();
        List<TopSoldProductDTO> TopProducts = chartService.getTopSoldProducts();

        model.addAttribute("product", new Product());
        model.addAttribute("products", Products);
        model.addAttribute("topProducts", TopProducts);
        model.addAttribute("Month", Month);
        model.addAttribute("Week", Week);
        return "statistics";
    }
}
