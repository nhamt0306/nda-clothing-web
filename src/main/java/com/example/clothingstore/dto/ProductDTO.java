package com.example.clothingstore.dto;

import com.example.clothingstore.model.BaseClassEntity;

public class ProductDTO extends BaseClassEntity {
    private String name;
    private String description;
    private String image;
    private Long category_id;

    public ProductDTO(String name, String description, String image, Long category_id) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }
}
