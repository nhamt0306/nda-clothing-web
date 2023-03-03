package com.example.clothingstore.service.impl;

import com.example.clothingstore.model.CommuneEntity;
import com.example.clothingstore.model.DistrictEntity;
import com.example.clothingstore.model.ProvinceEntity;
import com.example.clothingstore.repository.CommuneRepository;
import com.example.clothingstore.repository.DistrictRepository;
import com.example.clothingstore.repository.ProvinceRepository;
import com.example.clothingstore.service.CommuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommuneServiceImpl implements CommuneService {

    @Autowired
    CommuneRepository communeRepository;

    @Override
    public List<CommuneEntity> getCommuneByDistrictId(long id) {
        return communeRepository.findByDistrictEntityId(id);
    }

    @Override
    public boolean existById(Long id) {
        return communeRepository.existsById(id);
    }
}
