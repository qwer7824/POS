package com.pos.controller;

import com.pos.dto.SearchForm;
import com.pos.entity.Sale;
import com.pos.entity.SaleDetail;
import com.pos.repository.SaleRepository;
import com.pos.service.SaleDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SaleDetailController {

    private final SaleDetailService saleDetailService;
    private final SaleRepository saleRepository;


    @GetMapping(value = "/saleDetail/{saleId}")
    public String saleDetail(@PathVariable Long saleId, Model model){

        List<SaleDetail> saleDetails = saleDetailService.getSaleDetailsById(saleId);
        Sale sale = saleDetailService.getSaleById(saleId);

        int totalCount = 0;
        for(int i = 0;i<saleDetails.size();i++){
            totalCount += saleDetails.get(i).getCount();
        }

        String money = saleDetailService.totalPrice(sale.getPrice());

        model.addAttribute("totalCount",totalCount);
        model.addAttribute("money", money);
        model.addAttribute("saleDetails", saleDetails);
        model.addAttribute("sale", sale);

        return "saleDetail";
    }

    @GetMapping(value = "/saleList")
    public String saleList(Model model, SearchForm searchForm) {

        List<Sale> sales = saleDetailService.getToDaySales();
        int totalPrice = 0;
        for (int i = 0; i < sales.size(); i++) {
            totalPrice += sales.get(i).getPrice();
        }
        String money = saleDetailService.totalPrice(totalPrice);

        model.addAttribute("sales", sales);
        model.addAttribute("totalPrice", money);

        return "saleList";
    }
    @PostMapping(value = "/saleList")
    public String saleLists(Model model, SearchForm searchForm) {

        List<Sale> sales = saleDetailService.getSearchSales(searchForm);
        int totalPrice = 0;
        for (int i = 0; i < sales.size(); i++) {
            totalPrice += sales.get(i).getPrice();
        }
        String money = saleDetailService.totalPrice(totalPrice);

        model.addAttribute("searchForm", searchForm);
        model.addAttribute("sales", sales);
        model.addAttribute("totalPrice", money);

        return "saleList";
    }
}
