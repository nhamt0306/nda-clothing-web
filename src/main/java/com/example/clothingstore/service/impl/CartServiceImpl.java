package com.example.clothingstore.service.impl;

import com.example.clothingstore.model.CartEntity;
import com.example.clothingstore.repository.CartRepository;
import com.example.clothingstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;
    @Override
    public CartEntity save(Long id) {
        return cartRepository.save(new CartEntity(id));
    }
}
