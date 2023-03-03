package com.example.clothingstore.service.impl;

import com.example.clothingstore.model.ProvinceEntity;
import com.example.clothingstore.repository.ProvinceRepository;
import com.example.clothingstore.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceServiceImpl implements ProvinceService {
    @Autowired
    ProvinceRepository provinceRepository;

    @Override
    public List<ProvinceEntity> getAllProvince() {
        return provinceRepository.findAll();
    }

    @Override
    public boolean existById(Long id) {
        return provinceRepository.existsById(id);
    }
}
