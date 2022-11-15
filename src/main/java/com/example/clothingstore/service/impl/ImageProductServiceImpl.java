package com.example.clothingstore.service.impl;

import com.example.clothingstore.model.ImageProductEntity;
import com.example.clothingstore.model.ProductEntity;
import com.example.clothingstore.repository.ImageProductRepository;
import com.example.clothingstore.repository.ProductRepository;
import com.example.clothingstore.service.ImageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ImageProductServiceImpl implements ImageProductService {
    @Autowired
    ImageProductRepository imageProductRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CloudinaryService cloudinaryService;

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

    @Override
    public void uploadImage(long productId, MultipartFile image) {
        ImageProductEntity imageProductEntity = new ImageProductEntity();
        String imageUrl = cloudinaryService.uploadFile(image,String.valueOf(productId),
                "BookStore"+ "/" + "Product"+image);
        if(!imageUrl.equals("-1")) {
            imageProductEntity.setImage(imageUrl);
            imageProductEntity.setProductEntity(productRepository.findById(productId).get());
        }
        else if(imageProductEntity.getImage().equals("") || imageProductEntity.getImage().equals("-1"))
        {
            imageProductEntity.setImage("");
            imageProductEntity.setProductEntity(productRepository.findById(productId).get());
        }
        imageProductRepository.save(imageProductEntity);
    }
}
