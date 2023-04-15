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

    Page<ProductEntity> findAllByNameContaining(String keyword, Pageable pageable);

    @Query(value = "SELECT pro FROM ProductEntity pro " +
            "JOIN pro.typeEntities type " +
            "WHERE (?1 is null or pro.name LIKE %?1%) " +
            "AND (?2 is null or pro.categoryEntity.id = ?2) " +
            "AND (?3 is null or pro.avgRating >= ?3) " +
            "AND pro.status = 'Active' " +
            "GROUP BY pro.id")
    Page<ProductEntity> findAllByFiltering(String keyword, Long catId, Integer rating, Pageable pageable);

}
