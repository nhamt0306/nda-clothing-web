package com.example.clothingstore.service;

import com.example.clothingstore.model.CommentEntity;
import com.example.clothingstore.model.ProductEntity;

import java.util.List;

public interface CommentService {
    List<CommentEntity> getAllType();
    CommentEntity save(CommentEntity commentEntity);
    CommentEntity findById(Long id);
    List<CommentEntity> findByProductId(Long id);
    void delete(Long id);
    Boolean checkCommentIncludeByUser(Long userId, Long commentId);
    Long countCommentByProductId(Long product_id);
    List<CommentEntity> filterByRating(Long rating, Long productId);

    List<CommentEntity> findAllCommentPagingByProductId(Long productId,Integer pageNo, Integer pageSize, String sortBy);
}
