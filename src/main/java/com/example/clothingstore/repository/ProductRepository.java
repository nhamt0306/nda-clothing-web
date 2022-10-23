package com.example.clothingstore.repository;

import com.example.clothingstore.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity save(ProductEntity productEntity);
    List<ProductEntity> findByNameContaining(String name);

}
