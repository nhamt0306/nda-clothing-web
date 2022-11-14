package com.example.clothingstore.controller;

import com.example.clothingstore.config.LocalVariable;
import com.example.clothingstore.dto.CategoryDTO;
import com.example.clothingstore.mapper.CategoryMapper;
import com.example.clothingstore.model.CategoryEntity;
import com.example.clothingstore.service.CategorySerivce;
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

    @GetMapping("/category/getAll")
    public ResponseEntity<?> getAllCategory()
    {
        List<CategoryEntity> categoryEntityList = categorySerivce.getAllCategory();
        List<CategoryMapper> categoryMappers = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntityList)
        {
            CategoryMapper categoryMapper = new CategoryMapper(categoryEntity.getId(), categoryEntity.getName());
            categoryMappers.add(categoryMapper);
        }
        return ResponseEntity.ok(categoryMappers);
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
        try {
            return ResponseEntity.ok(categorySerivce.findCategoryById(id));
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(LocalVariable.messageCannotFindCat + id, HttpStatus.NOT_FOUND);
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
}
