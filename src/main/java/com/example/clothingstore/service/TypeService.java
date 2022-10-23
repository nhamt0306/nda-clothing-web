package com.example.clothingstore.service;

import com.example.clothingstore.model.TypeEntity;

import java.util.List;

public interface TypeService {
    List<TypeEntity> getAllType();
    TypeEntity save(TypeEntity type);
    TypeEntity findById(Long id);
    void delete(Long id);
}
