package com.example.clothingstore.service.impl;

import com.example.clothingstore.config.LocalVariable;
import com.example.clothingstore.model.CommentEntity;
import com.example.clothingstore.model.TypeEntity;
import com.example.clothingstore.repository.CommentRepository;
import com.example.clothingstore.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Override
    public List<CommentEntity> getAllType() {
        return commentRepository.findAll();
    }

    @Override
    public CommentEntity save(CommentEntity commentEntity) {
        return commentRepository.save(commentEntity);
    }

    @Override
    public CommentEntity findById(Long id) {
        return commentRepository.findById(id).get();
    }

    @Override
    public List<CommentEntity> findByProductId(Long id) {
        return commentRepository.getAllByProductIdOrderByCreate_atDesc(id);
    }

    @Override
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Boolean checkCommentIncludeByUser(Long userId, Long commentId) {
        return commentRepository.existsByUserIdAndId(userId, commentId);
    }

    @Override
    public Long countCommentByProductId(Long product_id) {
        return commentRepository.countProductCommentById(product_id);
    }
}
