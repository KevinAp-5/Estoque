package com.sistema_estoque.estoque.dto.mapper;

import org.springframework.stereotype.Component;

import com.sistema_estoque.estoque.dto.ProductDTO;
import com.sistema_estoque.estoque.model.Product;

@Component
public class ProductMapper {
    private ProductMapper() {}

    public static ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getCategory());
    }

    public static Product toModel(ProductDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Product(dto.id(), dto.name(), dto.price(), dto.category());
    }

}
