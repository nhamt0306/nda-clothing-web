package com.example.clothingstore.model;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.Setting;

import static org.springframework.data.elasticsearch.annotations.FieldType.Text;
import static org.springframework.data.elasticsearch.annotations.FieldType.Long;

import javax.persistence.Id;

@Document(indexName = "product")
@Setting(settingPath = "/index-config.json")
public class ElasticProduct {
    @Id
    private Long id;

    @Field(type = Text, analyzer = "custom_analyzer")
    private String name;

    @Field(type = Text, analyzer = "custom_analyzer")
    private String description;

    @Field()
    private String image;

    @Field(type = Text)
    private String avgRating;

    @Field(type = Text)
    private String price;

    @Field(type = Text)
    private String size;

    @Field()
    private String color;

    @Field(type = Text)
    private String sale;

    @Field(type = Text)
    private String sold;

    @Field(type = Text)
    private String quantity;

    @Field(type = Text)
    private String categoryId;

    @Field(type = Text, analyzer = "custom_analyzer")
    private String categoryName;

    @Field(type = Text)
    private String countComment;

    @Field(type = Text)
    private String status;

    public ElasticProduct(java.lang.Long id, String name, String description, String image, String avgRating, String price, String size, String color, String sale, String sold, String quantity, String categoryId, String categoryName, String countComment, String status) {
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

    public String getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(String avgRating) {
        this.avgRating = avgRating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCountComment() {
        return countComment;
    }

    public void setCountComment(String countComment) {
        this.countComment = countComment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
