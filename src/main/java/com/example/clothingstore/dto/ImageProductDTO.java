package com.example.clothingstore.dto;

public class ImageProductDTO {
    private Long id;
    private String image;
    private Long productId;

    public ImageProductDTO(Long id, String image, Long productId) {
        this.id = id;
        this.image = image;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
