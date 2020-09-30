package com.codegym.service;

import com.codegym.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<Product> findAllProduct();
    Product findById(Long id);
    void save(Product product);
    void remove(Long id);
}
