package com.sistema_estoque.estoque.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.sistema_estoque.estoque.dto.ProductDTO;
import com.sistema_estoque.estoque.service.ProductService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    public final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO productDTO, UriComponentsBuilder uri) {
        ProductDTO savedProduct = productService.createProduct(productDTO);
        URI uriProduct = buildUserUri(uri, savedProduct.id());
        return ResponseEntity.created(uriProduct).body(savedProduct);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        var products = productService.getAllAProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable @Positive @NotNull Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    private URI buildUserUri(UriComponentsBuilder uri, Long userId) {
        return uri.path("/api/products/" + userId).buildAndExpand(userId).toUri();
    }
}
