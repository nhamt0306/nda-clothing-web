package com.example.clothingstore.mapper;

import java.util.ArrayList;
import java.util.List;

public class CategoryPagingResponse {
    private List<CategoryMapper> list = new ArrayList<>();
    private Integer numberItem;

    public CategoryPagingResponse() {
    }

    public CategoryPagingResponse(List<CategoryMapper> categoryMapperList, Integer numberItem) {
        this.list = categoryMapperList;
        this.numberItem = numberItem;
    }

    public List<CategoryMapper> getCategoryMapperList() {
        return list;
    }

    public void setCategoryMapperList(List<CategoryMapper> categoryMapperList) {
        this.list = categoryMapperList;
    }

    public Integer getNumberItem() {
        return numberItem;
    }

    public void setNumberItem(Integer numberItem) {
        this.numberItem = numberItem;
    }
}
