package com.example.clothingstore.repository;

import com.example.clothingstore.model.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<TypeEntity, Long> {
    List<TypeEntity> findAll();
    Optional<TypeEntity> findById(Long id);
    List<TypeEntity> getAllByProductEntityId(Long productId);
    List<TypeEntity> getAllByStatus(String status);
    TypeEntity getTypeEntityByColorAndSizeAndProductEntityId(String color, Long size, Long productId);
    List<TypeEntity> getAllByProductEntityIdAndStatus(Long productId, String status);

    @Query(value = "SELECT * FROM clothing_store.types where color = :Color and size= :Size and product_id = :ProductId",nativeQuery = true)
    TypeEntity getTypeByColorSizeProductId(@Param("Color") String Color, @Param("Size") Long Size, @Param("ProductId") Long ProductId);

}
