package com.example.clothingstore.repository;

import com.example.clothingstore.model.CartProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CartProductRepository extends JpaRepository<CartProductEntity, Long> {
    @Query(value = "SELECT * FROM carts_products WHERE cart_id = :CartId",nativeQuery = true)
    List<CartProductEntity> getAllProductInCart(@Param("CartId") Long CartId);
    // check product exist in cart?
    Boolean existsByProductEntityIdAndColorAndSizeAndCartEntityId(Long prodcutId, String color, Long size, Long cartId);
    CartProductEntity save(CartProductEntity cartProductEntity);
    CartProductEntity findByCartEntityIdAndProductEntityIdAndColorAndSize(Long cartId, Long productId, String color, Long size);
    void deleteByCartEntityIdAndProductEntityIdAndColorAndSize(Long cartId, Long productId, String color, Long size);
}
