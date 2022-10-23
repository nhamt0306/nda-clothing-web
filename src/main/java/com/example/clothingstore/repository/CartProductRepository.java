package com.example.clothingstore.repository;

import com.example.clothingstore.model.CartProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProductRepository, Long> {
    @Query(value = "SELECT * FROM carts_products WHERE cart_id = :CartId",nativeQuery = true)
    List<CartProductEntity> getAllProductInCart(@Param("CartId") Long CartId);
    // check product exist in cart?
    Boolean existsByProductId(Long productId);
    CartProductEntity save(CartProductEntity cartProductEntity);
}
