package com.example.clothingstore.service.impl;

import com.example.clothingstore.model.CartProductEntity;
import com.example.clothingstore.model.TypeEntity;
import com.example.clothingstore.repository.CartProductRepository;
import com.example.clothingstore.security.principal.UserDetailService;
import com.example.clothingstore.service.CartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartProductServiceImpl implements CartProductService {
    @Autowired
    CartProductRepository cartProductRepository;
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    TypeServiceImpl typeService;

    @Override
    public CartProductEntity save(CartProductEntity cartProductEntity) {
        // Tim cart product tuong ung
        // kiem tra san pham co ton tai trong gio hang cua user do hay khong --> thieu check user
        // Check if add quantity + current quantity > available quantity or not
        TypeEntity type = typeService.getTypeByColorAndSizeAndProductId(cartProductEntity.getColor(), cartProductEntity.getSize(), cartProductEntity.getProductEntity().getId());

        if (existsByProduct(cartProductEntity.getProductEntity().getId(), cartProductEntity.getColor(), cartProductEntity.getSize(), cartProductEntity.getCartEntity().getId()))
        {
            CartProductEntity cartProduct = cartProductRepository.findByCartEntityIdAndProductEntityIdAndColorAndSize(cartProductEntity.getCartEntity().getId(), cartProductEntity.getProductEntity().getId(),cartProductEntity.getColor(), cartProductEntity.getSize());
            if(cartProduct.getQuantity() + cartProductEntity.getQuantity() > type.getQuantity()) {
                cartProduct.setQuantity(type.getQuantity());
            }
            else {
                cartProduct.setQuantity(cartProduct.getQuantity()+cartProductEntity.getQuantity());
            }
            return cartProductRepository.save(cartProduct);
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
    public Boolean existsByProduct(Long productId, String color, Long size, Long cartId) {
        return cartProductRepository.existsByProductEntityIdAndColorAndSizeAndCartEntityId(productId, color, size, cartId);
    }

    @Override
    public void deleteProductInCart(Long cartId, Long productId, String color, Long size) {
        cartProductRepository.deleteProductInCart(cartId, productId, color, size);
    }

    @Override
    public CartProductEntity increaseQuantity(Long productId, Long cartId, String color, Long size) {
        CartProductEntity cartProduct = cartProductRepository.findByCartEntityIdAndProductEntityIdAndColorAndSize(cartId, productId, color, size);
        cartProduct.setQuantity(cartProduct.getQuantity() + 1);
        return cartProductRepository.save(cartProduct);
    }

    @Override
    public CartProductEntity decreaseQuantity(Long productId, Long cartId, String color, Long size) {
        CartProductEntity cartProduct =cartProductRepository.findByCartEntityIdAndProductEntityIdAndColorAndSize(cartId, productId, color, size);
        if (cartProduct.getQuantity() == 1)
        {
            cartProductRepository.deleteProductInCart(cartId, productId, color, size);
            return null;
        }
        if (cartProduct.getQuantity() > 0) {
            cartProduct.setQuantity(cartProduct.getQuantity() - 1);
        }

        return cartProductRepository.save(cartProduct);
    }

    @Override
    public CartProductEntity setQuantity(Long productId, Long cartId, String color, Long size, Long quantity) {
        CartProductEntity cartProduct = cartProductRepository.findByCartEntityIdAndProductEntityIdAndColorAndSize(cartId, productId, color, size);
        cartProduct.setQuantity(quantity);
        return cartProductRepository.save(cartProduct);
    }
}
