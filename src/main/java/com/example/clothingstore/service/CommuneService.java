package com.example.clothingstore.service;

import com.example.clothingstore.model.CommuneEntity;
import com.example.clothingstore.model.DistrictEntity;
import com.example.clothingstore.model.ProvinceEntity;

import java.util.List;

public interface CommuneService {
    public List<CommuneEntity> getCommuneByDistrictId(long id);
    boolean existById(Long id);

}
