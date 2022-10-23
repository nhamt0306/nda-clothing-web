package com.example.clothingstore.repository;

import com.example.clothingstore.model.WishListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishListEntity, Long> {
}
