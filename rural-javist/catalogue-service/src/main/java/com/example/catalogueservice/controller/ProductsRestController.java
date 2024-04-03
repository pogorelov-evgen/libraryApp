package com.example.catalogueservice.controller;

import com.example.catalogueservice.controller.payload.NewProductPayLoad;
import com.example.catalogueservice.entity.Product;
import com.example.catalogueservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("catalogue-api/products")
public class ProductsRestController {

    private final ProductService productService;

    private final MessageSource messageSource;

    @GetMapping
    public List<Product> findProducts(){
        return this.productService.findAllProducts();
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody NewProductPayLoad payLoad,
                                                 BindingResult bindingResult, UriComponentsBuilder uriComponentsBuilder,
                                                 Locale locale){
        if(bindingResult.hasErrors()){
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                    this.messageSource.getMessage("errors.400.title", new Object[0], locale));
            problemDetail.setProperty("errors", bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            return ResponseEntity.badRequest().body(problemDetail);
        }
        else {
            Product product = this.productService.createProduct(payLoad.title(), payLoad.details());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/catalogue-api/products/{productId}")
                            .build(Map.of("productId", product.getId())))
                    .body(product);
        }
    }
}
