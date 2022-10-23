package com.example.clothingstore.repository;

import com.example.clothingstore.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity save(ProductEntity productEntity);
    List<ProductEntity> findByNameContaining(String name);

}
