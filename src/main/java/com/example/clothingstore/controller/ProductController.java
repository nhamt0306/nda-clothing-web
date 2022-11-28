package com.example.clothingstore.controller;

import com.example.clothingstore.config.LocalVariable;
import com.example.clothingstore.dto.ProductDTO;
import com.example.clothingstore.mapper.ProductMapper;
import com.example.clothingstore.mapper.ProductPagingResponse;
import com.example.clothingstore.model.CategoryEntity;
import com.example.clothingstore.model.ProductEntity;
import com.example.clothingstore.service.impl.CategoryServiceImpl;
import com.example.clothingstore.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")   //Để ghép AuthController với các controller khác
@RequestMapping
@RestController
public class ProductController {
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    CategoryServiceImpl categoryService;

    @GetMapping("/product/getAll")
    public ResponseEntity<?> getAllProduct()
    {
        List<ProductEntity> productEntityList = productService.getAllProduct();
        List<ProductMapper> responseProductList = new ArrayList<>();
        for (ProductEntity productEntity : productEntityList)
        {
            if (!productEntity.getTypeEntities().isEmpty())
            {
                ProductMapper productMapper = new ProductMapper(productEntity.getId(), productEntity.getName(), productEntity.getDescription(), productEntity.getImage(), productEntity.getAvgRating(), productEntity.getTypeEntities().get(0).getPrice(), productEntity.getTypeEntities().get(0).getSize(), productEntity.getTypeEntities().get(0).getColor(), productEntity.getTypeEntities().get(0).getSale(), productEntity.getTypeEntities().get(0).getSold(), productEntity.getTypeEntities().get(0).getQuantity(), productEntity.getCategoryEntity().getId(), productEntity.getCategoryEntity().getName());
                responseProductList.add(productMapper);
            }
            else
            {
                ProductMapper productMapper = new ProductMapper(productEntity.getId(), productEntity.getName(), productEntity.getDescription(), productEntity.getImage(), productEntity.getAvgRating(), Long.valueOf(0L), Long.valueOf(0L), "Đang cập nhật", Long.valueOf(0L), Long.valueOf(0L), Long.valueOf(0L), productEntity.getCategoryEntity().getId(), productEntity.getCategoryEntity().getName());
                responseProductList.add(productMapper);
            }
        }
        return ResponseEntity.ok(responseProductList);
    }

    @GetMapping("/product")
    public Object getAllProducts(@RequestParam(defaultValue = "1") Integer pageNo,
                                            @RequestParam(defaultValue = "100") Integer pageSize,
                                            @RequestParam(defaultValue = "id") String sortBy,
                                            @RequestParam(required = false) Long catId) {

        Integer maxPageSize;
        Integer maxPageNo;
        List<ProductEntity> productEntityList = new ArrayList<>();
        List<ProductEntity> numberItemList = new ArrayList<>();
        if (catId == null) {
            maxPageSize = productService.getAllProduct().size();
            if (pageSize > maxPageSize)
            {
                pageSize = maxPageSize;
            }
            maxPageNo = maxPageSize / pageSize;
            if (pageNo > maxPageNo)
            {
                pageNo = maxPageNo;
            }
            productEntityList = productService.getAllProductPaging(pageNo-1, pageSize, sortBy);
            numberItemList = productService.getAllProduct();
        }
        else {
            maxPageSize = productService.findProductByCat(catId).size();
            if (pageSize > maxPageSize)
            {
                pageSize = maxPageSize;
            }
            maxPageNo = maxPageSize / pageSize;
            if (pageNo > maxPageNo)
            {
                pageNo = maxPageNo;
            }
            productEntityList = productService.getAllProductByCatPaging(pageNo-1, pageSize, sortBy, catId);
            numberItemList = productService.findProductByCat(catId);
        }

        List<ProductMapper> responseProductList = new ArrayList<>();
        for (ProductEntity productEntity : productEntityList)
        {
            if (!productEntity.getTypeEntities().isEmpty())
            {
                ProductMapper productMapper = new ProductMapper(productEntity.getId(), productEntity.getName(), productEntity.getDescription(), productEntity.getImage(), productEntity.getAvgRating(), productEntity.getTypeEntities().get(0).getPrice(), productEntity.getTypeEntities().get(0).getSize(), productEntity.getTypeEntities().get(0).getColor(), productEntity.getTypeEntities().get(0).getSale(), productEntity.getTypeEntities().get(0).getSold(), productEntity.getTypeEntities().get(0).getQuantity(), productEntity.getCategoryEntity().getId(), productEntity.getCategoryEntity().getName());
                responseProductList.add(productMapper);
            }
            else
            {
                ProductMapper productMapper = new ProductMapper(productEntity.getId(), productEntity.getName(), productEntity.getDescription(), productEntity.getImage(), productEntity.getAvgRating(), Long.valueOf(0L), Long.valueOf(0L), "Đang cập nhật", Long.valueOf(0L), Long.valueOf(0L), Long.valueOf(0L), productEntity.getCategoryEntity().getId(), productEntity.getCategoryEntity().getName());
                responseProductList.add(productMapper);
            }
        }
        ProductPagingResponse productPagingResponse = new ProductPagingResponse(responseProductList, numberItemList.size());
        return productPagingResponse;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable long id){
        try {
            ProductEntity productEntity = productService.findProductById(id);
            if (!productEntity.getTypeEntities().isEmpty())
            {
                ProductMapper productMapper = new ProductMapper(productEntity.getId(), productEntity.getName(), productEntity.getDescription(), productEntity.getImage(), productEntity.getAvgRating(), productEntity.getTypeEntities().get(0).getPrice(), productEntity.getTypeEntities().get(0).getSize(), productEntity.getTypeEntities().get(0).getColor(), productEntity.getTypeEntities().get(0).getSale(), productEntity.getTypeEntities().get(0).getSold(), productEntity.getTypeEntities().get(0).getQuantity(), productEntity.getCategoryEntity().getId(), productEntity.getCategoryEntity().getName());
                return ResponseEntity.ok(productMapper);
            }
            else {
                return new ResponseEntity<>("Type of product is invalid!", HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Cannot find product with id = " + id, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/product/category/{id}")
    public ResponseEntity<?> getProductByCategory(@PathVariable long id){
        try {
            List<ProductEntity> productEntityList = productService.findProductByCat(id);
            List<ProductMapper> responseProductList = new ArrayList<>();
            for (ProductEntity productEntity : productEntityList)
            {
                ProductMapper productMapper = new ProductMapper(productEntity.getId(), productEntity.getName(), productEntity.getDescription(), productEntity.getImage(), productEntity.getAvgRating(), productEntity.getTypeEntities().get(0).getPrice(), productEntity.getTypeEntities().get(0).getSize(), productEntity.getTypeEntities().get(0).getColor(), productEntity.getTypeEntities().get(0).getSale(), productEntity.getTypeEntities().get(0).getSold(), productEntity.getTypeEntities().get(0).getQuantity(), productEntity.getCategoryEntity().getId(), productEntity.getCategoryEntity().getName());
                responseProductList.add(productMapper);
            }
            return ResponseEntity.ok(responseProductList);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Cannot find product with category id = " + id, HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/admin/product/create")
    public Object createProduct(@RequestBody ProductDTO productDTO) throws ParseException {
        ProductEntity productEntity = new ProductEntity();
        if (productDTO.getName().equals(null))
        {
            return "Tên danh mục không hợp lê!";
        }
        productEntity.setName(productDTO.getName());
        if (productDTO.getImage().equals(null))
        {
            return "Hình ảnh không hợp lê!";
        }
        productEntity.setAvgRating(Long.valueOf(5));
        productEntity.setImage(productDTO.getImage());
        productEntity.setDescription(productDTO.getDescription());
        CategoryEntity categoryEntity = categoryService.findCategoryById(productDTO.getCategory_id());
        if (categoryEntity == null)
        {
            return "Danh mục không tồn tại!";
        }
        productEntity.setCategoryEntity(categoryEntity);
        productEntity.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        productEntity.setCreate_at(new Timestamp(System.currentTimeMillis()));
        productService.save(productEntity);
        // return entity for front end
        if (!productEntity.getTypeEntities().isEmpty())
        {
            ProductMapper productMapper = new ProductMapper(productEntity.getId(), productEntity.getName(), productEntity.getDescription(), productEntity.getImage(), productEntity.getAvgRating(), productEntity.getTypeEntities().get(0).getPrice(), productEntity.getTypeEntities().get(0).getSize(), productEntity.getTypeEntities().get(0).getColor(), productEntity.getTypeEntities().get(0).getSale(), productEntity.getTypeEntities().get(0).getSold(), productEntity.getTypeEntities().get(0).getQuantity(), productEntity.getCategoryEntity().getId(), productEntity.getCategoryEntity().getName());
            return productMapper;
        }
        ProductMapper productMapper = new ProductMapper(productEntity.getId(), productEntity.getName(), productEntity.getDescription(), productEntity.getImage(), productEntity.getAvgRating(), Long.valueOf(0L), Long.valueOf(0L), "Đang cập nhật", Long.valueOf(0L), Long.valueOf(0L), Long.valueOf(0L), productEntity.getCategoryEntity().getId(), productEntity.getCategoryEntity().getName());
        return productMapper;
    }

    @DeleteMapping("/admin/product/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable long id)
    {
        productService.delete(id);
        return ResponseEntity.ok(LocalVariable.messageDeleteCatSuccess);
    }
}
