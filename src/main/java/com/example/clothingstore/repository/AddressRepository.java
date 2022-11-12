package com.example.clothingstore.repository;

import com.example.clothingstore.model.AddressEntity;
import com.example.clothingstore.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    List<AddressEntity> getAllByUserEntityId(Long userId);
    @Query(value = "SELECT * FROM addresses where user_id = :UserID and add_default =true;",nativeQuery = true)
    AddressEntity getDefaultAddressForUser(@Param("UserID") Long UserID);
}
