package com.example.clothingstore.service;

import com.example.clothingstore.model.CommuneEntity;
import com.example.clothingstore.model.DistrictEntity;
import com.example.clothingstore.model.ProvinceEntity;

import java.util.List;

public interface ProvinceService {
    public List<ProvinceEntity> getAllProvince();
    boolean existById(Long id);

}
