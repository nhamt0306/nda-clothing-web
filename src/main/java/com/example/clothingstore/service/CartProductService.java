package com.example.clothingstore.service;

import com.example.clothingstore.model.CartProductEntity;

import java.util.List;
import java.util.Optional;

public interface CartProductService {
    CartProductEntity save(CartProductEntity cartProductEntity);
    List<CartProductEntity> getAllProductByCartId(Long cartId);
    void delete(Long id);
    Boolean existsByProduct(Long productId, String color, Long size, Long cartId);
}
