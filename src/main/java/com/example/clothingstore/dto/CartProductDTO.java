package com.example.clothingstore.dto;

public class CartProductDTO {
    private Long quantity;
    private Long price;
    private String color;
    private Long size;
    private Long product_id;
    private String proName;
    private String proImage;

    public CartProductDTO(Long quantity, Long price, String color, Long size, Long product_id) {
        this.quantity = quantity;
        this.price = price;
        this.color = color;
        this.size = size;
        this.product_id = product_id;
    }

    public CartProductDTO(Long quantity, Long price, String color, Long size, Long product_id, String proName, String proImage) {
        this.quantity = quantity;
        this.price = price;
        this.color = color;
        this.size = size;
        this.product_id = product_id;
        this.proName = proName;
        this.proImage = proImage;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProImage() {
        return proImage;
    }

    public void setProImage(String proImage) {
        this.proImage = proImage;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
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

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }
}
