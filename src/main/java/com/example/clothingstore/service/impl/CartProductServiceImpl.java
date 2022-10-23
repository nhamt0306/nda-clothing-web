package com.example.clothingstore.service.impl;

import com.example.clothingstore.model.CartProductEntity;
import com.example.clothingstore.repository.CartProductRepository;
import com.example.clothingstore.service.CartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartProductServiceImpl implements CartProductService {
    @Autowired
    CartProductRepository cartProductRepository;
    @Override
    public CartProductEntity save(CartProductEntity cartProductEntity) {
        // Tim cart product tuong ung
        // kiem tra san pham co ton tai trong gio hang cua user do hay khong --> thieu check user
        if (existsByProduct(cartProductEntity.getProductEntity().getId()))
        {
            cartProductEntity.setQuantity(cartProductEntity.getQuantity()+1);
        }
        return cartProductRepository.save(cartProductEntity);
    }

    @Override
    public List<CartProductEntity> getAllProductByCartId(Long cartId) {
        return cartProductRepository.getAllProductInCart(cartId);
    }


    @Override
    public void delete(Long id) {
        cartProductRepository.deleteById(id);
    }

    @Override
    public Boolean existsByProduct(Long productId) {
        return cartProductRepository.existsByProductEntityId(productId);
    }
}
