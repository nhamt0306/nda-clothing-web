package com.example.clothingstore.service.impl;

import com.example.clothingstore.model.ImageProductEntity;
import com.example.clothingstore.repository.ImageProductRepository;
import com.example.clothingstore.service.ImageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ImageProductServiceImpl implements ImageProductService {
    @Autowired
    ImageProductRepository imageProductRepository;

    @Override
    public List<ImageProductEntity> getAllImageByProduct(Long productId) {
        return imageProductRepository.getAllByProductEntityId(productId);
    }

    @Override
    public ImageProductEntity save(ImageProductEntity imageProductEntity) {
        imageProductEntity.setCreate_at(new Timestamp(System.currentTimeMillis()));
        imageProductEntity.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        return imageProductRepository.save(imageProductEntity);
    }

    @Override
    public void delete(Long imageId) {
        imageProductRepository.deleteById(imageId);
    }
}
