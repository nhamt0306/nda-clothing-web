package com.example.clothingstore.service.impl;

import com.example.clothingstore.config.LocalVariable;
import com.example.clothingstore.model.CategoryEntity;
import com.example.clothingstore.model.ProductEntity;
import com.example.clothingstore.repository.CategoryRepository;
import com.example.clothingstore.service.CategorySerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategorySerivce {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<CategoryEntity> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryEntity save(CategoryEntity categoryEntity) {
        categoryEntity.setCreate_at(new Timestamp(System.currentTimeMillis()));
        categoryEntity.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        return categoryRepository.save(categoryEntity);
    }

    @Override
    public CategoryEntity findCategoryById(Long id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public List<CategoryEntity> findAllCategoryByName(String name) {
        return categoryRepository.findAllByName(name);
    }

    @Override
    public List<CategoryEntity> findByCatParentId(Long id) {
        return categoryRepository.findAllByParentId(id);
    }

    @Override
    public List<CategoryEntity> findAllCategoryActive() {
        return categoryRepository.findAllByStatus(LocalVariable.activeStatus);
    }

    @Override
    public void deleteCategoryById(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).get();
        if (categoryEntity.getStatus().equals(LocalVariable.activeStatus)) {
            categoryEntity.setStatus(LocalVariable.disableStatus);
        }
        else {
            categoryEntity.setStatus(LocalVariable.activeStatus);
        }
        categoryEntity.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        categoryRepository.save(categoryEntity);
    }

    @Override
    public List<CategoryEntity> getAllCatPaging(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<CategoryEntity> pagedResult = categoryRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<CategoryEntity>();
        }
    }


}
