package com.example.clothingstore.repository;

import com.example.clothingstore.model.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Page<OrderEntity> findAllByUserEntityId(Long userId, Pageable pageable);
    List<OrderEntity> getAllByUserEntityIdAndStatus(Long userEntities_id, String status);
    List<OrderEntity> getAllByUserEntityIdAndStatusNot(Long userEntities_id, String status);

    @Query(value = "select * from clothing_store.orders where user_id = :UserId order by create_at desc",nativeQuery = true)
    List<OrderEntity> getAllByUserEntitiesIdOrderByCreate_atDesc(@Param("UserId") Long UserId);

    @Query(value = "select * from orders order by create_at desc",nativeQuery = true)
    List<OrderEntity> getAllOrderByDate();


    boolean existsByUserEntityId(Long userid);
}
