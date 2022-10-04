package com.example.clothingstore.model;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseClassEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String status = "Active";
    private String parentId;

    // Relationship with table ProductEntity
    @OneToOne(mappedBy = "categoryEntity")
    private ProductEntity productEntity;

    // Constructor

    public CategoryEntity() {
    }

    public CategoryEntity(Long id, String name, String status, String parentId) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.parentId = parentId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
