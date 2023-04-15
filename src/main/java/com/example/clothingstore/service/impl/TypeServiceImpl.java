package com.example.clothingstore.service.impl;

import com.example.clothingstore.config.LocalVariable;
import com.example.clothingstore.dto.ColorAndTypeDTO;
import com.example.clothingstore.model.TypeEntity;
import com.example.clothingstore.repository.TypeRepository;
import com.example.clothingstore.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    TypeRepository typeRepository;


    @Override
    public List<TypeEntity> getAll() {
        return typeRepository.getAllByStatus("Active");
    }

    @Override
    public TypeEntity save(TypeEntity type) {
        return typeRepository.save(type);
    }

    @Override
    public TypeEntity findTypeById(Long id) {
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

    @Override
    public List<TypeEntity> getAllTypeByProduct(Long proId) {
        return typeRepository.getAllByProductEntityId(proId);
    }

    @Override
    public List<TypeEntity> getAllActiveTypeByProduct(Long proId) {
        return typeRepository.getAllByProductEntityIdAndStatus(proId, LocalVariable.activeStatus);
    }

    @Override
    public TypeEntity getTypeByColorAndSizeAndProductId(String color, Long size, Long productId) {
        return typeRepository.getTypeByColorSizeProductId(color, size, productId);
    }

    @Override
    public ColorAndTypeDTO getListColorAndSize(Long productId) {
        List<TypeEntity> typeEntities = typeRepository.getAllByProductEntityId(productId);
        List<String> colorList = new ArrayList<>();
        List<Long> sizeList = new ArrayList<>();
        for (TypeEntity type : typeEntities)
        {
            if (type.getStatus().equals(LocalVariable.activeStatus)) {
                if (!colorList.contains(type.getColor())) {
                    colorList.add(type.getColor());
                }
                if (!sizeList.contains(type.getSize())) {
                    sizeList.add(type.getSize());
                }
            }
        }
        ColorAndTypeDTO colorAndTypeDTO = new ColorAndTypeDTO(productId, colorList, sizeList);
        return colorAndTypeDTO;
    }
}
