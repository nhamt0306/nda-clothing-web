package com.example.clothingstore.repository;

import com.example.clothingstore.model.AddressEntity;
import com.example.clothingstore.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    List<CommentEntity> getAllByUserEntityId(Long userId);

}
