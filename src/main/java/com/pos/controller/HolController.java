package com.pos.controller;

import com.pos.entity.Hol;
import com.pos.entity.SaleCart;
import com.pos.repository.HolRepository;
import com.pos.repository.SaleCartRepository;
import com.pos.service.HolService;
import com.pos.service.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HolController {
    private final HolService holService;
    private final SaleService saleService;

    @GetMapping(value = "/")
    public String main(Model model) {
        List<Hol> hols = holService.holList();
        int size = hols.size();
        model.addAttribute("hols", hols);
        model.addAttribute("size", size);

        Map<Integer,Integer> totalPrice = saleService.getTotalPrice();
        List<SaleCart> saleCart = saleService.findSaleCart();

        model.addAttribute("saleCart", saleCart);
        model.addAttribute("totalPrice", totalPrice);
        return "main";
    }


}
