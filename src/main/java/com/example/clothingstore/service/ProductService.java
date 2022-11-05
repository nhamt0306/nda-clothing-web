package com.example.clothingstore.service;

import com.example.clothingstore.model.ProductEntity;

import java.util.List;

public interface ProductService {
    List<ProductEntity> getAllProduct();
    ProductEntity save(ProductEntity productEntity);
    ProductEntity findProductById(Long id);
    List<ProductEntity> findProductByName(String name);
    void delete(Long id);
    List<ProductEntity> findProductByCat(Long catId);
}
