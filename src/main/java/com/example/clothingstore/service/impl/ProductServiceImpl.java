package com.example.clothingstore.service.impl;

import com.example.clothingstore.config.LocalVariable;
import com.example.clothingstore.model.ProductEntity;
import com.example.clothingstore.repository.ProductRepository;
import com.example.clothingstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<ProductEntity> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public ProductEntity save(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    @Override
    public ProductEntity findProductById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public List<ProductEntity> findProductByName(String name) {
        return productRepository.findAllByNameContaining(name);
    }

    @Override
    public void delete(Long id) {
        ProductEntity productEntity = productRepository.findById(id).get();
        productEntity.setStatus(LocalVariable.disableStatus);
        productEntity.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        productEntity.setCreate_at(new Timestamp(System.currentTimeMillis()));
        productRepository.save(productEntity);
    }

    @Override
    public List<ProductEntity> findProductByCat(Long catId) {
        return productRepository.findAllByCategoryEntityId(catId);
    }
}
