package com.sistema_estoque.estoque.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sistema_estoque.estoque.dto.ProductDTO;
import com.sistema_estoque.estoque.dto.ProductMapper;
import com.sistema_estoque.estoque.exceptions.ProductNotFoundException;
import com.sistema_estoque.estoque.model.Product;
import com.sistema_estoque.estoque.repository.ProductRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
public class ProductService {
    public final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDTO createProduct(@Valid ProductDTO productDTO) {
        Product product = new Product(productDTO);
        return ProductMapper.toDTO(productRepository.save(product));
    }

    public List<ProductDTO> getAllAProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    public ProductDTO getProductById(@Positive @NotNull Long id) {
        var product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found with ID: " + id));
        return ProductMapper.toDTO(product);
    }
}
