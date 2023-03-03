package com.example.clothingstore.config.mapper;

public class CategoryMapper {
    private Long id;
    private String name;

    private Long productQuantity;

    private String status;

    public CategoryMapper(Long id, String name, Long productQuantity, String status) {
        this.id = id;
        this.name = name;
        this.productQuantity = productQuantity;
        this.status = status;
    }

    public Long getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Long productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryMapper(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
