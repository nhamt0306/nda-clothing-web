package com.example.clothingstore.dto;

public class CommentDTO {
    private Long id;
    private String comContent;
    private Long comRating;
    private Long userId;
    private Long productId;

    private Long transactionId;

    public CommentDTO(Long id, String comContent, Long comRating, Long userId, Long productId, Long transactionId) {
        this.id = id;
        this.comContent = comContent;
        this.comRating = comRating;
        this.userId = userId;
        this.productId = productId;
        this.transactionId = transactionId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComContent() {
        return comContent;
    }

    public void setComContent(String comContent) {
        this.comContent = comContent;
    }

    public Long getComRating() {
        return comRating;
    }

    public void setComRating(Long comRating) {
        this.comRating = comRating;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
