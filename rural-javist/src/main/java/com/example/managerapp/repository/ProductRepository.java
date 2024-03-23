package com.example.managerapp.repository;

import com.example.managerapp.entity.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

    Product save(Product product);
}
