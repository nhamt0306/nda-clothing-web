package com.example.clothingstore.repository;

import com.example.clothingstore.model.DistrictEntity;
import com.example.clothingstore.model.ProvinceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<DistrictEntity, Long> {
    List<DistrictEntity> findByProvinceEntityId(long id);
    Boolean existsById(long id);

}
