package com.example.clothingstore.dto;

public class WishListDTO {
    private Long productId;
    private String color;
    private Long size;

    public WishListDTO(Long productId, Long userId) {
        this.productId = productId;
    }

    public WishListDTO() {
    }

    public WishListDTO(Long productId, String color, Long size) {
        this.productId = productId;
        this.color = color;
        this.size = size;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

}
