package com.example.clothingstore.repository;

import com.example.clothingstore.model.WishListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WishListRepository extends JpaRepository<WishListEntity, Long> {
    List<WishListEntity> getAllByUserEntityId(Long userId);
}
