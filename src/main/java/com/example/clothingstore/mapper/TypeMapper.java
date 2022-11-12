package com.example.clothingstore.mapper;

public class TypeMapper {
    private Long quantity;
    private Long price;
    private Long size;
    private String color;
    private Long sale;
    private Long sold;
    private Long productId;

    public TypeMapper(Long quantity, Long price, Long size, String color, Long sale, Long sold) {
        this.quantity = quantity;
        this.price = price;
        this.size = size;
        this.color = color;
        this.sale = sale;
        this.sold = sold;
    }

    public TypeMapper(Long quantity, Long price, Long size, String color, Long sale, Long sold, Long productId) {
        this.quantity = quantity;
        this.price = price;
        this.size = size;
        this.color = color;
        this.sale = sale;
        this.sold = sold;
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
}
