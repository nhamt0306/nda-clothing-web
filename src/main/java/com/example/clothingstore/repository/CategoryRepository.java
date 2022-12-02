package com.example.clothingstore.repository;

import com.example.clothingstore.model.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findAllByName(String name);
    List<CategoryEntity> findAllByParentId(Long id);
    List<CategoryEntity> findAllByStatus(String status);
    Page<CategoryEntity> getAllByStatus(String status, Pageable pageable);
}
