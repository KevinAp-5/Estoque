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

import com.sistema_estoque.estoque.model.Product;
import com.sistema_estoque.estoque.service.ProductService;

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
    public ResponseEntity<Product> createProduct(@RequestBody Product product, UriComponentsBuilder uri) {
        Product savedProduct = productService.createProduct(product);
        URI uriProduct = buildUserUri(uri, savedProduct.getId());
        return ResponseEntity.created(uriProduct).body(savedProduct);
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> getAllProducts() {
        var products = productService.getAllAProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") @Positive @NotNull Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    private URI buildUserUri(UriComponentsBuilder uri, Long userId) {
        return uri.path("/api/products/" + userId).buildAndExpand(userId).toUri();
    }
}
