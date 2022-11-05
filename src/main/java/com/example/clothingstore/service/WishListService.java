package com.example.clothingstore.service;

import com.example.clothingstore.model.WishListEntity;

import java.util.List;

public interface WishListService {
    List<WishListEntity> findAll();
    WishListEntity findById(Long id);
    WishListEntity save(WishListEntity wishListEntity);
    void delete(Long id);
    WishListEntity addToWishlist(Long productId, Long userId);
    Boolean existByProduct(Long productId, Long userId);
    void deleteByProductIdAndUserId(Long productId, Long userId);
    List<WishListEntity> findAllByUser(Long userId);
}
