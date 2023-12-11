package com.pos.controller;

import com.pos.dto.ProductDto;
import com.pos.entity.Product;
import com.pos.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
@Controller
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/new") // 판매상품 등록 페이지
    public String addNewProductForm(Model model){
        model.addAttribute("product", new Product());
        return "addNewProductForm";
    }

    @PostMapping(value = "/new")
    public String addNewProduct(@Validated @ModelAttribute("product") ProductDto product) {
        productService.addNewProduct(product);
        return "redirect:statistics";
    }
}
