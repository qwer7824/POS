package com.pos.controller;

import com.pos.dto.ProductDto;
import com.pos.entity.Product;
import com.pos.repository.ProductRepository;
import com.pos.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    @GetMapping(value = "/new") // 판매상품 등록 페이지
    public String addNewProductForm(Model model){
        model.addAttribute("product", new Product());
        return "addNewProductForm";
    }

    @PostMapping(value = "/new")
    public String addNewProduct(@Valid @ModelAttribute("product") ProductDto product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addNewProductForm";
        }
        productService.addNewProduct(product);
        return "redirect:/statistics";
    }

    @PostMapping(value = "/edit")
    public String editNewProduct(@Valid @ModelAttribute("product") ProductDto productdto,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/statistics";
        }
       productService.updateProduct(productdto);
        return "redirect:/statistics";
    }
    @GetMapping(value = "/product/{id}")
    @ResponseBody
    public Optional<Product> getProduct(@PathVariable("id") Long id){
        return productRepository.findById(id);
    }
}
