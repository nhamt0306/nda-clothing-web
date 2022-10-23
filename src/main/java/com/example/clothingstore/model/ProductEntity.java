package com.example.clothingstore.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Long sale;
    private Long sold;
    private String image;
    private Long avgRating;
    private String status = "Active";

    // Relationship with table TransactionEntity
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<TransactionEntity> transactionEntities = new ArrayList<>();

    // Relationship with table CartProductEntity
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<CartProductEntity> cartProductEntities = new ArrayList<>();

    // Relationship with table CommentEntity
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<CommentEntity> commentEntities = new ArrayList<>();

    // Relationship with table TypeEntity
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<TypeEntity> typeEntities =new ArrayList<>();

    // Relationship with table BrandEntity
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    private CategoryEntity categoryEntity;

    // Relationship with table WishList
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<WishListEntity> wishListEntities =new ArrayList<>();

    // Constructor, Getter and Setter
    public ProductEntity() {
    }

    public ProductEntity(Long id, String name, String description, Long sale, Long sold, String image, Long avgRating, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sale = sale;
        this.sold = sold;
        this.image = image;
        this.avgRating = avgRating;
        this.status = status;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSale() {
        return sale;
    }

    public void setSale(Long sale) {
        this.sale = sale;
    }

    public Long getSold() {
        return sold;
    }

    public void setSold(Long sold) {
        this.sold = sold;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Long avgRating) {
        this.avgRating = avgRating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

