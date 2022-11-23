package com.example.clothingstore.service.impl;

import com.example.clothingstore.config.LocalVariable;
import com.example.clothingstore.model.CategoryEntity;
import com.example.clothingstore.model.ProductEntity;
import com.example.clothingstore.repository.ProductRepository;
import com.example.clothingstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CloudinaryService cloudinaryService;

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

    @Override
    public boolean existByProductId(Long id) {
        return productRepository.existsById(id);
    }

    @Override
    public ProductEntity uploadImage(long id, MultipartFile image) {
        ProductEntity productEntity = productRepository.findById(id).get();
        String imageUrl = cloudinaryService.uploadFile(image,String.valueOf(id),
                "BookStore"+ "/" + "Product");
        if(!imageUrl.equals("-1")) {
            productEntity.setImage(imageUrl);
        }
        else if(productEntity.getImage().equals("") || productEntity.getImage().equals("-1"))
            productEntity.setImage("");

        return productRepository.save(productEntity);
    }

    @Override
    public List<ProductEntity> getAllProductPaging(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<ProductEntity> pagedResult = productRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<ProductEntity>();
        }
    }

    @Override
    public List<ProductEntity> getAllProductByCatPaging(Integer pageNo, Integer pageSize, String sortBy, Long catId) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<ProductEntity> pagedResult = productRepository.findAllByCategoryEntityId(catId, paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<ProductEntity>();
        }
    }
}
