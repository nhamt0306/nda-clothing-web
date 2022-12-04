package com.example.clothingstore.mapper;

public class TransactionMapper {
    private Long id;
    private Long tranUnitPrice;
    private Long tranQuantity;
    private Long productId;
    private String color;
    private Long size;
    private String productImage;
    private String productName;
    private Boolean isCommented;

    public TransactionMapper(Long id, Long tranUnitPrice, Long tranQuantity, Long productId, String productImage, String productName, String color, Long size) {
        this.id = id;
        this.tranUnitPrice = tranUnitPrice;
        this.tranQuantity = tranQuantity;
        this.productId = productId;
        this.productImage = productImage;
        this.productName = productName;
        this.color = color;
        this.size = size;
    }

    public TransactionMapper(Long id, Long tranUnitPrice, Long tranQuantity, Long productId, String color, Long size, String productImage, String productName, Boolean isCommented) {
        this.id = id;
        this.tranUnitPrice = tranUnitPrice;
        this.tranQuantity = tranQuantity;
        this.productId = productId;
        this.color = color;
        this.size = size;
        this.productImage = productImage;
        this.productName = productName;
        this.isCommented = isCommented;
    }

    public Boolean getCommented() {
        return isCommented;
    }

    public void setCommented(Boolean commented) {
        isCommented = commented;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getTranUnitPrice() {
        return tranUnitPrice;
    }

    public void setTranUnitPrice(Long tranUnitPrice) {
        this.tranUnitPrice = tranUnitPrice;
    }

    public Long getTranQuantity() {
        return tranQuantity;
    }

    public void setTranQuantity(Long tranQuantity) {
        this.tranQuantity = tranQuantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
