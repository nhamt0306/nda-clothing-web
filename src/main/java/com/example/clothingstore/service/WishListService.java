package com.example.clothingstore.service;

import com.example.clothingstore.model.WishListEntity;

import java.util.List;

public interface WishListService {
    List<WishListEntity> getAll();
    WishListEntity save(WishListEntity type);
    WishListEntity findById(Long id);
    void delete(Long id);
    List<WishListEntity> findAllByUser(Long userId);
}
