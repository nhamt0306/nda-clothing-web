package com.example.clothingstore.controller;

import com.example.clothingstore.config.LocalVariable;
import com.example.clothingstore.dto.CategoryDTO;
import com.example.clothingstore.mapper.CategoryMapper;
import com.example.clothingstore.mapper.CategoryPagingResponse;
import com.example.clothingstore.mapper.ProductMapper;
import com.example.clothingstore.mapper.ProductPagingResponse;
import com.example.clothingstore.model.CategoryEntity;
import com.example.clothingstore.model.ProductEntity;
import com.example.clothingstore.service.CategorySerivce;
import com.example.clothingstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping
@RestController
public class CategoryController {
    @Autowired
    CategorySerivce categorySerivce;

    @Autowired
    ProductService productService;

    @GetMapping("/category/getAll")
    public ResponseEntity<?> getAllCategory()
    {
        List<CategoryEntity> categoryEntityList = categorySerivce.getAllCategory();
        List<CategoryMapper> categoryMappers = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntityList)
        {
            // get all product by category
            List<ProductEntity> productEntity = productService.findProductByCat(categoryEntity.getId());

            int productQuantity = productEntity.size();
            String status = categoryEntity.getStatus();

            CategoryMapper categoryMapper = new CategoryMapper(categoryEntity.getId(), categoryEntity.getName(),
                    Long.valueOf(productQuantity), status);
            categoryMappers.add(categoryMapper);
        }
        return ResponseEntity.ok(categoryMappers);
    }

    // category paging
    @GetMapping("/category")
    public Object getAllCategory(@RequestParam(defaultValue = "1") Integer pageNo,
                                 @RequestParam(defaultValue = "100") Integer pageSize,
                                 @RequestParam(defaultValue = "id") String sortBy) {
        Integer maxPageSize;
        Integer maxPageNo;
        List<CategoryEntity> categoryEntityList = new ArrayList<>();

        maxPageSize = categorySerivce.findAllCategoryActive().size();
        if (pageSize > maxPageSize)
        {
            pageSize = 12;
        }
        maxPageNo = maxPageSize / pageSize;
        if (pageNo > maxPageNo +1)
        {
            pageNo = maxPageNo +1;
        }
        categoryEntityList = categorySerivce.getAllCatPaging(pageNo-1, pageSize);

        List<CategoryMapper> categoryMappers = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntityList)
        {
            // get all product by category
            List<ProductEntity> productEntity = productService.findProductByCat(categoryEntity.getId());

            int productQuantity = productEntity.size();
            String status = categoryEntity.getStatus();

            CategoryMapper categoryMapper = new CategoryMapper(categoryEntity.getId(), categoryEntity.getName(),
                    Long.valueOf(productQuantity), status);
            categoryMappers.add(categoryMapper);
        }

        CategoryPagingResponse categoryPagingResponse = new CategoryPagingResponse(categoryMappers, maxPageSize);
        return categoryPagingResponse;
    }

    @GetMapping("/category/getAllActive")
    public ResponseEntity<?> getAllCategoryActive()
    {
        List<CategoryEntity> categoryEntityList = categorySerivce.findAllCategoryActive();
        List<CategoryMapper> categoryMappers = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntityList)
        {
            CategoryMapper categoryMapper = new CategoryMapper(categoryEntity.getId(), categoryEntity.getName());
            categoryMappers.add(categoryMapper);
        }
        return ResponseEntity.ok(categoryMappers);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable long id){
        CategoryEntity categoryEntity = categorySerivce.findCategoryById(id);
        if (categoryEntity == null) {
            return new ResponseEntity<>(LocalVariable.messageCannotFindCat + id, HttpStatus.NOT_FOUND);
        }
        else {
            CategoryMapper categoryMapper = new CategoryMapper(categoryEntity.getId(), categoryEntity.getName());
            return ResponseEntity.ok(categoryMapper);
        }
    }

    @GetMapping("/category/parentid/{id}")
    public ResponseEntity<?> getCategoryByParent(@PathVariable long id){
        try {
            List<CategoryEntity> categoryEntityList = categorySerivce.findByCatParentId(id);
            List<CategoryMapper> categoryMappers = new ArrayList<>();
            for (CategoryEntity categoryEntity : categoryEntityList)
            {
                CategoryMapper categoryMapper = new CategoryMapper(categoryEntity.getId(), categoryEntity.getName());
                categoryMappers.add(categoryMapper);
            }
            return ResponseEntity.ok(categoryMappers);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(LocalVariable.messageCannotFindCat + id, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/admin/category/createCategory")
    public Object createCategory(@RequestBody CategoryDTO categoryDTO) throws ParseException {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryDTO.getName());
        categoryEntity.setParentId(categoryDTO.getParend_id());
        categorySerivce.save(categoryEntity);
        CategoryMapper categoryMapper = new CategoryMapper(categoryEntity.getId(), categoryEntity.getName());
        return categoryMapper;
    }

    @DeleteMapping("/admin/category/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable long id)
    {
        categorySerivce.deleteCategoryById(id);
        return ResponseEntity.ok(LocalVariable.messageDeleteCatSuccess);
    }

    @PutMapping("/admin/category/{id}")
    public Object updateCategory(@PathVariable long id, @RequestBody CategoryDTO categoryDTO) throws ParseException {
        CategoryEntity categoryEntity = categorySerivce.findCategoryById(id);
        if (categoryEntity == null) {
            return new ResponseEntity<>(LocalVariable.messageCannotFindCat + id, HttpStatus.NOT_FOUND);
        }

        categoryEntity.setName(categoryDTO.getName());
        categoryEntity.setParentId(categoryDTO.getParend_id());
        categorySerivce.save(categoryEntity);
        CategoryMapper categoryMapper = new CategoryMapper(categoryEntity.getId(), categoryEntity.getName());
        return categoryMapper;
    }
}
