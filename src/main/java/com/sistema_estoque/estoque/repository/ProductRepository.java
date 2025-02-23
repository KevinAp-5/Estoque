package com.sistema_estoque.estoque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.sistema_estoque.estoque.model.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @NonNull Optional<Product> findById(@NonNull Long id);
}
