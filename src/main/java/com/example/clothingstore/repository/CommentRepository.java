package com.example.clothingstore.repository;

import com.example.clothingstore.model.CommentEntity;
import com.example.clothingstore.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByProductEntityId(Long id);
    Boolean existsByUserIdAndId(Long userId, Long commentId);

    @Query(value = "select * from clothing_store.comments where product_id = :ProductId order by create_at desc",nativeQuery = true)
    List<CommentEntity> getAllByProductIdOrderByCreate_atDesc(@Param("ProductId") Long ProductId);
    @Query(value = "Select Count(*) from clothing_store.comments where product_id = :ProductId",nativeQuery = true)
    Long countProductCommentById(@Param("ProductId") Long ProductId);
}
