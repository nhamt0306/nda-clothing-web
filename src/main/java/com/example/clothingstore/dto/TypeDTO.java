package com.example.clothingstore.dto;

public class TypeDTO {
    private String color;
    private Long size;
    private Long quantity;
    private Long price;
    private Long product_id;

    public TypeDTO(String color, Long size, Long quantity, Long price, Long product_id) {
        this.color = color;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.product_id = product_id;
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

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }
}
