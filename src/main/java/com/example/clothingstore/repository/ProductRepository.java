package com.example.clothingstore.repository;

import com.example.clothingstore.model.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity save(ProductEntity productEntity);
    List<ProductEntity> findByNameContaining(String name);
    List<ProductEntity> findByCategoryEntityNameContaining(String name);
    List<ProductEntity> findAllByCategoryEntityId(Long id);
    boolean existsById(Long productId);
    Page<ProductEntity> findAllByCategoryEntityId(Long id, Pageable pageable);

    @Query(value = "SELECT p FROM clothing_store.products p where p.name like %:keyword% or p.description like %:keyword%", nativeQuery = true)
    List<ProductEntity> searchProduct(@Param("keyword") String keyword);

    List<ProductEntity> findByDescriptionContaining(String name);
}
