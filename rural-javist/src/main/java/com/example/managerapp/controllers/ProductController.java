package com.example.managerapp.controllers;

import com.example.managerapp.entity.Product;
import com.example.managerapp.payload.UpdateProductPayLoad;
import com.example.managerapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("catalogue/products/{product:\\d+}")
public class ProductController {

    private final ProductService productService;

    @ModelAttribute("product")
    public Product product(@PathVariable("product") int productId) {
        return this.productService.findProduct(productId).orElseThrow();
    }

    @GetMapping()
    public String getProduct(){
        return "catalogue/products/product";
    }

    @GetMapping("edit")
    public String getProductEditPage(){
        return "catalogue/products/edit";
    }

    @PostMapping("edit")
    public String updateProduct(@ModelAttribute("product") Product product, UpdateProductPayLoad payLoad){
        this.productService.updateProduct(product.getId(), payLoad.title(), payLoad.details());
        return "redirect:/catalogue/products/%d".formatted(product.getId());
    }
    @GetMapping("edits")
    public String test(){
        return "catalogue/products/edits";
    }
}
