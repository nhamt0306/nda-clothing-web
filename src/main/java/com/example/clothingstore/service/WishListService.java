package com.example.clothingstore.service;

import com.example.clothingstore.model.WishListEntity;

import java.util.List;

public interface WishListService {
    List<WishListEntity> findAll();
    WishListEntity findById(Long id);
    WishListEntity save(WishListEntity wishListEntity);
    void delete(Long id);
}
