package com.sistema_estoque.estoque.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.sistema_estoque.estoque.dto.ProductDTO;
import com.sistema_estoque.estoque.dto.ResponseMessage;
import com.sistema_estoque.estoque.dto.UpdateProductDTO;
import com.sistema_estoque.estoque.service.ProductService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for managing products in the CRUD inventory system.
 * Provides endpoints for creating, retrieving, updating, and deleting products.
 * 
 * Author: Keven
 */
@RestController
@RequestMapping("/api/products/")
@Slf4j
public class ProductController {
    public final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Creates a new product.
     *
     * @param productDTO The DTO containing product information.
     * @param uri The UriComponentsBuilder for building the response URI.
     * @return ResponseEntity containing the created ProductDTO and the location URI.
     */
    @PostMapping()
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO productDTO, UriComponentsBuilder uri) {
        log.info("Received request to CREATE product: {}", productDTO);
        ProductDTO savedProduct = productService.createProduct(productDTO);
        URI uriProduct = buildUserUri(uri, savedProduct.id());
        log.info("Product created successfully with ID: {}", savedProduct.id());
        return ResponseEntity.created(uriProduct).body(savedProduct);
    }

    /**
     * Retrieves all products.
     *
     * @return ResponseEntity containing a list of ProductDTOs.
     */
    @GetMapping()
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        log.info("Received request to GET all products");
        var products = productService.getAllAProducts();
        log.info("Retrieved {} products", products.size());
        return ResponseEntity.ok(products);
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product to retrieve.
     * @return ResponseEntity containing the ProductDTO corresponding to the given ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable @Positive @NotNull Long id) {
        log.info("Received request to GET product with ID: {}", id);
        ProductDTO product = productService.getProductById(id);
        log.info("Retrieved product: {}", product);
        return ResponseEntity.ok(product);
    }

    /**
     * Updates an existing product by its ID with the provided DTO.
     *
     * @param id The ID of the product to update.
     * @param dto The DTO containing updated information for the product.
     * @return ResponseEntity containing the updated ProductDTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProductById(@PathVariable @Positive @NotNull Long id, @RequestBody @Valid UpdateProductDTO dto) {
        log.info("Received request to UPDATE product with ID: {}", id);
        ProductDTO updatedProduct = productService.updateProductById(id, dto);
        log.info("Updated product: {}", updatedProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete.
     * @return ResponseEntity containing a message indicating successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteProductById(@PathVariable @Positive @NotNull Long id) {
        log.info("Received request to DELETE product with ID: {}", id);
        productService.deleteProductById(id);
        log.info("Product deleted successfully with ID: {}", id);
        return ResponseEntity.ok(new ResponseMessage("Product deleted successfully."));
    }

    private URI buildUserUri(UriComponentsBuilder uri, Long userId) {
        return uri.path("/api/products/" + userId).buildAndExpand(userId).toUri();
    }
}
