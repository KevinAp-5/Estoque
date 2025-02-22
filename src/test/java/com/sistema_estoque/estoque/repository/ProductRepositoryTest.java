package com.sistema_estoque.estoque.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import com.sistema_estoque.estoque.model.Product;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get Product sucessfully")
    void testFindByIdSuccess() {
        Product product = Product.builder()
                .name("Parafuso 3 polegadas")
                .price(new BigDecimal(1.2))
                .category("parafuso")
                .build();

        this.createUser(product);
        Optional<Product> result =  productRepository.findById(product.getId());
        assertThat(result).isPresent();
    }

    @Test
    @DisplayName("Should not get Product from DB when user not exists")
    void testeFindByIdFail() {
        Long unrealisticProductID = 9999999L;
        Optional<Product> result = productRepository.findById(unrealisticProductID);
        assertThat(result).isEmpty();
    }

    private Product createUser(Product product) {
        this.entityManager.persist(product);
        return product;
    }
}
