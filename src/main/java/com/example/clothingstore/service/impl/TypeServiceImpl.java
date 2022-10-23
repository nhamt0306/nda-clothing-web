package com.example.clothingstore.service.impl;

import com.example.clothingstore.config.LocalVariable;
import com.example.clothingstore.model.TypeEntity;
import com.example.clothingstore.repository.TypeRepository;
import com.example.clothingstore.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    TypeRepository typeRepository;

    @Override
    public List<TypeEntity> getAllType() {
        return typeRepository.findAll();
    }

    @Override
    public TypeEntity save(TypeEntity type) {
        return typeRepository.save(type);
    }

    @Override
    public TypeEntity findById(Long id) {
        return typeRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        TypeEntity type = typeRepository.findById(id).get();
        type.setStatus(LocalVariable.disableStatus);
        type.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        type.setCreate_at(new Timestamp(System.currentTimeMillis()));
        typeRepository.save(type);
    }
}
