package com.example.clothingstore.service.impl;

import com.example.clothingstore.config.LocalVariable;
import com.example.clothingstore.model.CommentEntity;
import com.example.clothingstore.model.TypeEntity;
import com.example.clothingstore.repository.CommentRepository;
import com.example.clothingstore.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

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
        return commentRepository.findAllByProductEntityId(id);
    }

    @Override
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
