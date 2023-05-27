package com.example.clothingstore.service;

import com.example.clothingstore.model.ElasticProduct;
import com.example.clothingstore.model.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<ProductEntity> getAllProduct();
    ProductEntity save(ProductEntity productEntity);
    ProductEntity findProductById(Long id);
    List<ProductEntity> findProductByName(String name);
    void delete(Long id);
    List<ProductEntity> findProductByCat(Long catId);
    boolean existByProductId(Long id);
    ProductEntity uploadImage(long id, MultipartFile image);
    Page<ProductEntity> getAllProductByFiltering(
            Integer pageNo,
            Integer pageSize,
            String sortBy,
            Long catId,
            Integer rating,
            String keyword
    );
    List<ProductEntity> getAllProductPaging(Integer pageNo, Integer pageSize, String sortBy);
    List<ProductEntity> getAllProductByCatPaging(Integer pageNo, Integer pageSize, String sortBy, Long catId);
    List<ProductEntity> searchProduct(String keyword);
    List<ProductEntity> searchByCategoryName(String keyword);
    List<ProductEntity> searchByDescription(String keyword);
    List<ProductEntity> getAllProductByKeyword(Integer pageNo, Integer pageSize, String sortBy, String keyword);

//    Page<ElasticProduct> getElasticSearchProductByFiltering(Integer pageNo,
//                                                            Integer pageSize,
//                                                            String sortBy,
//                                                            Long catId,
//                                                            Integer rating,
//                                                            String keyword);
}
