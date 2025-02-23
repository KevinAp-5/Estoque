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
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {
    public final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Creates a new product.
     *
     * @param productDTO The DTO containing product information.
     * @return The created ProductDTO.
     */
    @Transactional
    public ProductDTO createProduct(@Valid ProductDTO productDTO) {
        log.info("Creating product: {}", productDTO);
        Product product = new Product(productDTO);
        Product savedProduct = productRepository.save(product);
        log.info("Product created successfully with ID: {}", savedProduct.getId());
        return ProductMapper.toDTO(savedProduct);
    }

    /**
     * Retrieves all products.
     *
     * @return A list of ProductDTOs.
     */
    public List<ProductDTO> getAllAProducts() {
        log.info("Retrieving all products");
        List<Product> products = productRepository.findAll();
        log.info("Retrieved {} products", products.size());
        return products.stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product to retrieve.
     * @return The ProductDTO corresponding to the given ID.
     * @throws ProductNotFoundException if no product is found with the given ID.
     */
    public ProductDTO getProductById(@Positive @NotNull Long id) {
        log.info("Retrieving product with ID: {}", id);
        var product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found with ID: " + id));
        log.info("Retrieved product: {}", product);
        return ProductMapper.toDTO(product);
    }

    /**
     * Updates an existing product with the provided DTO.
     *
     * @param dto The DTO containing updated product information.
     * @return The updated ProductDTO.
     * @throws IllegalArgumentException if the ID in the DTO is null.
     * @throws ProductNotFoundException if no product is found with the given ID.
     */
    @Transactional
    public ProductDTO updateProduct(@NotNull @Valid ProductDTO dto) {
        if (dto.id() == null) {
            throw new IllegalArgumentException("Product ID must not be null.");
        }

        log.info("Updating product with ID: {}", dto.id());
        var product = productRepository.findById(dto.id()).orElseThrow(
                () -> new ProductNotFoundException("Product not found with ID: " + dto.id()));

        // Update fields
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setCategory(dto.category());

        Product savedProduct = productRepository.save(product);
        log.info("Updated product: {}", savedProduct);
        return ProductMapper.toDTO(savedProduct);
    }

    /**
     * Updates an existing product by its ID with the provided DTO.
     *
     * @param id The ID of the product to update.
     * @param dto The DTO containing updated information for the product.
     * @return The updated ProductDTO.
     * @throws ProductNotFoundException if no product is found with the given ID.
     */
    @Transactional
    public ProductDTO updateProductById(@Positive @NotNull Long id, @NotNull @Valid UpdateProductDTO dto) {
        log.info("Updating product by ID: {}", id);
        var result = productRepository.findById(id).orElseThrow(
            () -> new ProductNotFoundException("Product not found with ID: " + id)
        );

        // Update fields
        result.setName(dto.name());
        result.setPrice(dto.price());
        result.setCategory(dto.category());
        
        Product savedProduct = productRepository.save(result);
        log.info("Updated product by ID: {} successfully", id);
        return ProductMapper.toDTO(savedProduct);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete.
     * @throws ProductNotFoundException if no product is found with the given ID.
     */
    @Transactional
    public void deleteProductById(@Positive @NotNull Long id) {
        log.info("Attempting to delete product with ID: {}", id);
        var productToDelete = productRepository.findById(id).orElseThrow(
            () -> new ProductNotFoundException("Product to be deleted not found with ID: " + id)
        );
        
        // Perform deletion
        productRepository.delete(productToDelete);
        log.info("Deleted product successfully with ID: {}", id);
    }
}
