package com.example.clothingstore.repository;

import com.example.clothingstore.model.CartProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Repository
public interface CartProductRepository extends JpaRepository<CartProductEntity, Long> {
    @Query(value = "SELECT * FROM carts_products WHERE cart_id = :CartId",nativeQuery = true)
    List<CartProductEntity> getAllProductInCart(@Param("CartId") Long CartId);
    // check product exist in cart?
    Boolean existsByProductEntityIdAndColorAndSizeAndCartEntityId(Long prodcutId, String color, Long size, Long cartId);
    CartProductEntity save(CartProductEntity cartProductEntity);
    CartProductEntity findByCartEntityIdAndProductEntityIdAndColorAndSize(Long cartId, Long productId, String color, Long size);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM clothing_store.carts_products WHERE cart_id = :CartId and product_id = :ProductId and color =:Color and size = :Size",nativeQuery = true)
    void deleteProductInCart(@Param("CartId") Long CartId, @Param("ProductId") Long ProductId,@Param("Color") String Color, @Param("Size")Long Size);
}
