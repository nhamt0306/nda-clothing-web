package com.example.clothingstore.repository;

import com.example.clothingstore.model.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
