package com.example.clothingstore.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "wishlists")
public class WishListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long price;
    private String color;
    private Long size;
    private Long catId;

    // Relationship with table ProductEntity
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "productId", nullable = false, referencedColumnName = "id")
    private ProductEntity productEntity;

    // Relationship with table ProductEntity
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userId", nullable = false, referencedColumnName = "id")
    private UserEntity userEntity;


    public WishListEntity() {
    }

    public WishListEntity(Long id, Long price, String color, Long size) {
        this.id = id;
        this.price = price;
        this.color = color;
        this.size = size;
    }

    public WishListEntity(Long id, ProductEntity productEntity, UserEntity userEntity) {
        this.id = id;
        this.productEntity = productEntity;
        this.userEntity = userEntity;
    }

    public WishListEntity(String color, Long size, ProductEntity productEntity, UserEntity userEntity) {
        this.color = color;
        this.size = size;
        this.productEntity = productEntity;
        this.userEntity = userEntity;
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
