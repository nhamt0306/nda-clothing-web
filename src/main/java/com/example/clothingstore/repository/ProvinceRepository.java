package com.example.clothingstore.repository;

import com.example.clothingstore.model.AddressEntity;
import com.example.clothingstore.model.ProvinceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<ProvinceEntity, Long> {
    List<ProvinceEntity> findAll();
    Boolean existsById(long id);
}
