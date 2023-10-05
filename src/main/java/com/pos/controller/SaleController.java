package com.pos.controller;

import com.pos.entity.*;
import com.pos.repository.HolRepository;
import com.pos.service.HolService;
import com.pos.service.ProductService;
import com.pos.service.SaleDetailService;
import com.pos.service.SaleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SaleController {

    private final ProductService productService;
    private final SaleService saleService;
    private final HolService holService;

    @GetMapping(value = "/sale/{hid}")
    public String sale(@PathVariable int hid , Model model) {
        List<Product> productList = productService.productList();
        model.addAttribute("productList", productList);

        List<SaleCart> saleCart = saleService.saleCartList(hid);
        model.addAttribute("saleCart",saleCart);

        int totalCost = saleService.calculateTotalCost(saleCart);
        model.addAttribute("totalCost", totalCost);

        LocalDateTime firstTime = holService.findTime(hid);
        model.addAttribute("firstTime", firstTime);
        return "sale";
    }

    @PostMapping(value = "/sale/add/{hid}")
    public String add(@PathVariable int hid,HttpServletRequest request){
        Long pid = Long.valueOf(request.getParameter("pid"));
        saleService.saleCartAdd(hid, pid);
        return "redirect:/sale/" + hid;
    }
    @PostMapping("/sale/delete/{hid}/{pid}")
    public String delete(@PathVariable int hid, @PathVariable("pid") Long pid){
        saleService.deleteSaleCart(hid, pid);
        return "redirect:/sale/" + hid;
    }
    @PostMapping("/sale/deleteEA/{hid}/{pid}")
    public String deleteEA(@PathVariable int hid, @PathVariable("pid") Long pid){
        saleService.deleteEASaleCart(hid, pid);
        return "redirect:/sale/" + hid;
    }
    @PostMapping("/sale/addEA/{hid}/{pid}")
    public String addEA(@PathVariable int hid, @PathVariable("pid") Long pid){
        saleService.addEASaleCart(hid, pid);
        return "redirect:/sale/" + hid;
    }

    @GetMapping("/sale/calculate/{hid}")
    public String calculate(@PathVariable int hid){
        saleService.sale(hid);
        return "redirect:/sale/" + hid;
    }
}