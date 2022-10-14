package com.example.clothingstore.dto;

public class CategoryDTO {
    private String name;
    private Long parend_id;

    public CategoryDTO(String name, Long parend_id) {
        this.name = name;
        this.parend_id = parend_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParend_id() {
        return parend_id;
    }

    public void setParend_id(Long parend_id) {
        this.parend_id = parend_id;
    }
}
