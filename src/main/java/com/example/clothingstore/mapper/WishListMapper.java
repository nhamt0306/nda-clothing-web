package com.example.clothingstore.mapper;

public class WishListMapper {
    private Long id;
    private String name;
    private Long price;
    private String image;
    private Long catId;
    private String catName;

    public WishListMapper(Long id, String name, Long price, String image, Long catId, String catName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.catId = catId;
        this.catName = catName;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
}
