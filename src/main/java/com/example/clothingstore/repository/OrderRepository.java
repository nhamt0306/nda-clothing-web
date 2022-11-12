package com.example.clothingstore.repository;

import com.example.clothingstore.model.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Page<OrderEntity> findAllByUserEntityId(Long userId, Pageable pageable);
    List<OrderEntity> getAllByUserEntityIdAndStatus(Long userEntities_id, String status);
    List<OrderEntity> getAllByUserEntityIdAndStatusNot(Long userEntities_id, String status);
    List<OrderEntity> getAllByUserEntityId(Long userid);
}
