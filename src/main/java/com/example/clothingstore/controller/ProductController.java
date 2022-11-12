package com.example.clothingstore.controller;

import com.example.clothingstore.config.LocalVariable;
import com.example.clothingstore.dto.ProductDTO;
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
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable long id){
        try {
            return ResponseEntity.ok(productService.findProductById(id));
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(LocalVariable.messageCannotFindCat + id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/product/category/{id}")
    public ResponseEntity<?> getProductByCategory(@PathVariable long id){
        try {
            return ResponseEntity.ok(productService.findProductByCat(id));
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(LocalVariable.messageCannotFindCat + id, HttpStatus.NOT_FOUND);
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
        return productService.save(productEntity);
    }

    @DeleteMapping("/admin/product/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable long id)
    {
        productService.delete(id);
        return ResponseEntity.ok(LocalVariable.messageDeleteCatSuccess);
    }
}
