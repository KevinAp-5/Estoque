package com.sistema_estoque.estoque.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductDTO(Long id, @NotBlank String name, @NotNull BigDecimal price, @NotBlank String category) {

}
