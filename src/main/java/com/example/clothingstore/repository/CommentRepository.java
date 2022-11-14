package com.example.clothingstore.repository;

import com.example.clothingstore.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByProductEntityId(Long id);
    Boolean existsByUserIdAndId(Long userId, Long commentId);
}
