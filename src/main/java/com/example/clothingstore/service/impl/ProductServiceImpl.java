package com.example.clothingstore.service.impl;

import com.example.clothingstore.config.LocalVariable;
import com.example.clothingstore.dto.ErrorResponse;
import com.example.clothingstore.dto.ResourceNotFoundException;
import com.example.clothingstore.model.CategoryEntity;
import com.example.clothingstore.model.ProductEntity;
import com.example.clothingstore.model.TypeEntity;
import com.example.clothingstore.repository.ProductRepository;
import com.example.clothingstore.service.ProductService;
import org.hibernate.mapping.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<ProductEntity> findProductByName(String name) {
        return productRepository.findByNameContaining(name);
    }

    @Override
    public void delete(Long id) {
        ProductEntity productEntity = productRepository.findById(id).get();
        if (productEntity.getStatus().equals(LocalVariable.activeStatus)) {
            productEntity.setStatus(LocalVariable.disableStatus);
        }
        else {
            productEntity.setStatus(LocalVariable.activeStatus);
        }
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
                "ClothingStore"+ "/" + "Product");
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

    @Override
    public Page<ProductEntity> getAllProductByFiltering(
        Integer pageNo,
        Integer pageSize,
        String sortBy,
        Long catId,
        Integer rating,
        String keyword
    ) {
        try {
            Page<ProductEntity> productEntities;
            Pageable paging;

            if (sortBy.charAt(0) == '-') {
                paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy.substring(1)));
            } else {
                paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
            }

            productEntities = productRepository.findAllByFiltering(keyword, catId, rating, paging);

            return productEntities;
        } catch(Exception e) {
            throw new ResourceNotFoundException("Check your params");
        }
    }

    @Override
    public List<ProductEntity> searchProduct(String keyword) {
        return productRepository.findByNameContaining(keyword);
    }

    @Override
    public List<ProductEntity> searchByCategoryName(String keyword) {
        return productRepository.findByCategoryEntityNameContaining(keyword);
    }

    @Override
    public List<ProductEntity> searchByDescription(String keyword) {
        return productRepository.findByDescriptionContaining(keyword);
    }

    @Override
    public List<ProductEntity> getAllProductByKeyword(Integer pageNo, Integer pageSize, String sortBy, String keyword) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<ProductEntity> pagedResult = productRepository.findAllByNameContaining(keyword, paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<ProductEntity>();
        }
    }
}
