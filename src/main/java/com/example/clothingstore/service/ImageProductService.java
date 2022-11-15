package com.example.clothingstore.service;

import com.example.clothingstore.model.ImageProductEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageProductService {
    List<ImageProductEntity> getAllImageByProduct(Long productId);
    ImageProductEntity save(ImageProductEntity imageProductEntity);
    void delete(Long imageId);
    void uploadImage(long productId, MultipartFile image);
}
