package com.example.clothingstore.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "images_product")
public class ImageProductEntity extends BaseClassEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;

    // Relationship with table ProductEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "productId", nullable = false, referencedColumnName = "id")
    private ProductEntity productEntity;

    // Getter, setter and constructor

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public ImageProductEntity() {
    }

    public ImageProductEntity(Long id, String image) {
        this.id = id;
        this.image = image;
    }

    public ImageProductEntity(Long id, ProductEntity productEntity) {
        this.id = id;
        this.productEntity = productEntity;
    }
}
