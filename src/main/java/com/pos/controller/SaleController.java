package com.pos.controller;

import com.pos.entity.Product;
import com.pos.entity.SaleCart;
import com.pos.entity.SaleCartDetail;
import com.pos.service.ProductService;
import com.pos.service.SaleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SaleController {

    private final ProductService productService;
    private final SaleService saleService;

    @GetMapping(value = "/sale/{hid}")
    public String sale(@PathVariable Long hid , Model model) {
        List<Product> productList = productService.productList();
        model.addAttribute("productList", productList);

        List<SaleCart> saleCartList = saleService.findByCartByHid(hid);
        model.addAttribute("saleCartList",saleCartList);

        model.addAttribute("hid",hid);

        List<SaleCartDetail> saleCartDetailList = new ArrayList<>();

        int totalCost = 0;
        if(saleCartList!=null){
            for(int i=0;i<saleCartList.size();i++){
                Product product = productService.findById(saleCartList.get(i).getPid());
                totalCost += (product.getPrice()*saleCartList.get(i).getCount());
                SaleCartDetail saleCartDetail = new SaleCartDetail(saleCartList.get(i).getHid(),
                        saleCartList.get(i).getPid(), product.getName(), product.getPrice(),
                        saleCartList.get(i).getCount());
                saleCartDetailList.add(saleCartDetail);
            }
        }
        model.addAttribute("totalCost", totalCost);
        model.addAttribute("saleCartDetailList", saleCartDetailList);

        return "sale";
    }

    @PostMapping(value = "/sale/add/{hid}")
    public String add(@PathVariable Long hid,HttpServletRequest request){
        Long pid = Long.valueOf(request.getParameter("pid"));
        int count = 1;
        log.info("log {}", pid);
        saleService.addCart(hid, pid, count);

        return "redirect:/sale/" + hid;
    }
    @PostMapping("/sale/delete/{hid}/{pid}")
    public String delete(@PathVariable Long hid, @PathVariable("pid") Long pid){

        saleService.deleteSaleCart(hid, pid);

        return "redirect:/sale/" + hid;
    }
    @PostMapping("/sale/deleteEA/{hid}/{pid}")
    public String deleteEA(@PathVariable Long hid, @PathVariable("pid") Long pid){

        saleService.deleteEASaleCart(hid, pid);

        return "redirect:/sale/" + hid;
    }
    @PostMapping("/sale/addEA/{hid}/{pid}")
    public String addEA(@PathVariable Long hid, @PathVariable("pid") Long pid){

        saleService.addEASaleCart(hid, pid);

        return "redirect:/sale/" + hid;
    }
}