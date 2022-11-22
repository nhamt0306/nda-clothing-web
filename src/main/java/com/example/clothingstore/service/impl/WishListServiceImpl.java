package com.example.clothingstore.service.impl;

import com.example.clothingstore.model.WishListEntity;
import com.example.clothingstore.repository.WishListRepository;
import com.example.clothingstore.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WishListServiceImpl implements WishListService {
    @Autowired
    WishListRepository wishListRepository;

    @Override
    public List<WishListEntity> getAll() {
        return wishListRepository.findAll();
    }

    @Override
    public WishListEntity save(WishListEntity wishlist) {
        return wishListRepository.save(wishlist);
    }

    @Override
    public WishListEntity findById(Long id) {
        return wishListRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        wishListRepository.deleteById(id);
    }

    @Override
    public List<WishListEntity> findAllByUser(Long userId) {
        return wishListRepository.getAllByUserEntityId(userId);
    }
}
