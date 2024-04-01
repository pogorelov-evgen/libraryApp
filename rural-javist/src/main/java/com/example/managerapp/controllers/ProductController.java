package com.example.managerapp.controllers;

import com.example.managerapp.entity.Product;
import com.example.managerapp.payload.UpdateProductPayLoad;
import com.example.managerapp.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("catalogue/products/{product:\\d+}")
public class ProductController {

    private final ProductService productService;

    @ModelAttribute("product")
    public Product product(@PathVariable("product") int productId) {
        return this.productService.findProduct(productId).orElseThrow(()-> new NoSuchElementException("Товар не найден"));
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

    @PostMapping("delete")
    public String deleteProduct(@ModelAttribute("product") Product product){
        this.productService.deleteProduct(product.getId());
        return "redirect:/catalogue/products/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model, HttpServletResponse response){
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error", exception.getMessage());
        return "errors/404";
    }

}
