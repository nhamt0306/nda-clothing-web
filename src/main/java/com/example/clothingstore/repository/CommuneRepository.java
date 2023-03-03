package com.example.clothingstore.repository;

import com.example.clothingstore.model.CommentEntity;
import com.example.clothingstore.model.CommuneEntity;
import com.example.clothingstore.model.DistrictEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommuneRepository extends JpaRepository<CommuneEntity, Long> {
    List<CommuneEntity> findByDistrictEntityId(long id);
    Boolean existsById(long id);
}
