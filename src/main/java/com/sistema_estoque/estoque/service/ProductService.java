package com.sistema_estoque.estoque.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema_estoque.estoque.dto.ProductDTO;
import com.sistema_estoque.estoque.dto.UpdateProductDTO;
import com.sistema_estoque.estoque.dto.mapper.ProductMapper;
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

    @Transactional
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

    @Transactional
    public ProductDTO updateProduct(@NotNull @Valid ProductDTO dto) {
        if (dto.id() == null) {
            throw new IllegalArgumentException("Product ID must not be null.");
        }

        var product = productRepository.findById(dto.id()).orElseThrow(
                () -> new ProductNotFoundException("Product not found with ID: " + dto.id()));

        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setCategory(dto.category());

        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDTO(savedProduct);
    }

    @Transactional
    public ProductDTO updateProductById(@Positive @NotNull Long id, @NotNull @Valid UpdateProductDTO dto) {
        var result = productRepository.findById(id).orElseThrow(
            () -> new ProductNotFoundException("Product not found with ID: " + id)
        );

        result.setName(dto.name());
        result.setPrice(dto.price());
        result.setCategory(dto.category());
        Product savedProduct = productRepository.save(result);
        return ProductMapper.toDTO(savedProduct);
    }
}
