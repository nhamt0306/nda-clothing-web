package com.example.clothingstore.controller;

import com.example.clothingstore.config.LocalVariable;
import com.example.clothingstore.dto.CategoryDTO;
import com.example.clothingstore.model.CategoryEntity;
import com.example.clothingstore.service.CategorySerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@CrossOrigin(origins = "*")
@RequestMapping
@RestController
public class CategoryController {
    @Autowired
    CategorySerivce categorySerivce;

    @GetMapping("/category/getAll")
    public ResponseEntity<?> getAllCategory()
    {
        return ResponseEntity.ok(categorySerivce.getAllCategory());
    }

    @GetMapping("/category/getAllActive")
    public ResponseEntity<?> getAllCategoryActive()
    {
        return ResponseEntity.ok(categorySerivce.findAllCategoryActive());
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
            return ResponseEntity.ok(categorySerivce.findByCatParentId(id));
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
        return categorySerivce.save(categoryEntity);
    }

    @DeleteMapping("/admin/category/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable long id)
    {
        categorySerivce.deleteCategoryById(id);
        return ResponseEntity.ok(LocalVariable.messageDeleteCatSuccess);
    }
}
