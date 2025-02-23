package com.sistema_estoque.estoque.model;

import java.math.BigDecimal;

import com.sistema_estoque.estoque.dto.ProductDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "products")
@Table(name="products")
@Data
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;
    private String category;

    public Product(ProductDTO dto) {
        this.id = dto.id();
        this.name = dto.name();
        this.price = dto.price();
        this.category = dto.category();
    }

    public Product(Long id, String name, BigDecimal price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }
}
