package com.example.clothingstore.model;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import static org.springframework.data.elasticsearch.annotations.FieldType.Text;
import static org.springframework.data.elasticsearch.annotations.FieldType.Long;
import javax.persistence.Id;

@Document(indexName = "product")
public class ElasticProduct {
    @Id
    private Long id;

    @Field(type = Text)
    private String name;

    @Field(type = Text)
    private String description;

    @Field()
    private String image;

    @Field(type = Long)
    private Long avgRating;

    @Field(type = Long)
    private Long price;

    @Field(type = Long)
    private Long size;

    @Field()
    private String color;

    @Field(type = Long)
    private Long sale;

    @Field(type = Long)
    private Long sold;

    @Field(type = Long)
    private Long quantity;

    @Field(type = Long)
    private Long categoryId;

    @Field()
    private String categoryName;

    @Field(type = Long)
    private Long countComment;

    @Field()
    private String status;

    public ElasticProduct(java.lang.Long id, String name, String description, String image, java.lang.Long avgRating, java.lang.Long price, java.lang.Long size, String color, java.lang.Long sale, java.lang.Long sold, java.lang.Long quantity, java.lang.Long categoryId, String categoryName, java.lang.Long countComment, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.avgRating = avgRating;
        this.price = price;
        this.size = size;
        this.color = color;
        this.sale = sale;
        this.sold = sold;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.countComment = countComment;
        this.status = status;
    }

    public java.lang.Long getId() {
        return id;
    }

    public void setId(java.lang.Long id) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public java.lang.Long getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(java.lang.Long avgRating) {
        this.avgRating = avgRating;
    }

    public java.lang.Long getPrice() {
        return price;
    }

    public void setPrice(java.lang.Long price) {
        this.price = price;
    }

    public java.lang.Long getSize() {
        return size;
    }

    public void setSize(java.lang.Long size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public java.lang.Long getSale() {
        return sale;
    }

    public void setSale(java.lang.Long sale) {
        this.sale = sale;
    }

    public java.lang.Long getSold() {
        return sold;
    }

    public void setSold(java.lang.Long sold) {
        this.sold = sold;
    }

    public java.lang.Long getQuantity() {
        return quantity;
    }

    public void setQuantity(java.lang.Long quantity) {
        this.quantity = quantity;
    }

    public java.lang.Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(java.lang.Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public java.lang.Long getCountComment() {
        return countComment;
    }

    public void setCountComment(java.lang.Long countComment) {
        this.countComment = countComment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
