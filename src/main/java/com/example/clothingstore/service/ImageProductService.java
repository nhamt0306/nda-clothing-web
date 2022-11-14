package com.example.clothingstore.service;

import com.example.clothingstore.model.ImageProductEntity;

import java.util.List;

public interface ImageProductService {
    List<ImageProductEntity> getAllImageByProduct(Long productId);
    ImageProductEntity save(ImageProductEntity imageProductEntity);
    void delete(Long imageId);
}
