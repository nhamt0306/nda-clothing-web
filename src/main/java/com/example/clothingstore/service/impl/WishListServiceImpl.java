package com.example.clothingstore.service.impl;

import com.example.clothingstore.model.ProductEntity;
import com.example.clothingstore.model.UserEntity;
import com.example.clothingstore.model.WishListEntity;
import com.example.clothingstore.repository.ProductRepository;
import com.example.clothingstore.repository.UserRepository;
import com.example.clothingstore.repository.WishListRepository;
import com.example.clothingstore.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class WishListServiceImpl implements WishListService {
    @Autowired
    WishListRepository wishListRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<WishListEntity> findAll() {
        return wishListRepository.findAll();
    }

    @Override
    public WishListEntity findById(Long id) {
        return wishListRepository.findById(id).get();
    }

    @Override
    public WishListEntity save(WishListEntity wishListEntity) {
        return wishListRepository.save(wishListEntity);
    }

    @Override
    public void delete(Long id) {
        wishListRepository.deleteById(id);
    }

    @Override
    public WishListEntity addToWishlist(Long productId, Long userId) {
        UserEntity user = userRepository.findById(userId).get();
        ProductEntity productEntity = productRepository.findById(productId).get();
        WishListEntity wishListEntity = new WishListEntity();
        wishListEntity.setCreate_at(new Timestamp(System.currentTimeMillis()));
        wishListEntity.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        wishListEntity.setUserEntity(user);
        wishListEntity.setProductEntity(productEntity);
        return wishListRepository.save(wishListEntity);
    }
}
