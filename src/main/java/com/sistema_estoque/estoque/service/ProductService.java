package com.sistema_estoque.estoque.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sistema_estoque.estoque.exceptions.ProductNotFoundException;
import com.sistema_estoque.estoque.model.Product;
import com.sistema_estoque.estoque.repository.ProductRepository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
public class ProductService {
    public final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(@NotNull Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllAProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(@Positive @NotNull Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found with ID: " + id)
            );
    }
}
