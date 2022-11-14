package com.example.clothingstore.service;

import com.example.clothingstore.model.CommentEntity;

import java.util.List;

public interface CommentService {
    List<CommentEntity> getAllType();
    CommentEntity save(CommentEntity commentEntity);
    CommentEntity findById(Long id);
    List<CommentEntity> findByProductId(Long id);
    void delete(Long id);
    Boolean checkCommentIncludeByUser(Long userId, Long commentId);
}
