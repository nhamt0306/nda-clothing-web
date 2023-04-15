package com.example.clothingstore.repository;

import com.example.clothingstore.model.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Boolean existsByUserIdAndId(Long userId, Long commentId);

    @Query(value = "select * from clothing_store.comments where product_id = :ProductId order by create_at desc",nativeQuery = true)
    List<CommentEntity> getAllByProductIdOrderByCreate_atDesc(@Param("ProductId") Long ProductId);
    @Query(value = "Select Count(*) from clothing_store.comments where product_id = :ProductId",nativeQuery = true)
    Long countProductCommentById(@Param("ProductId") Long ProductId);

    List<CommentEntity> findAllByRatingAndProductEntityId(Long rating, Long productId);

    Page<CommentEntity> findAllByProductEntityId(Long id, Pageable pageable);
}
