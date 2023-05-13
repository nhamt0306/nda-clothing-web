package com.example.clothingstore.dto;

import lombok.Data;

@Data
public class CommentDTOV1 {
    private Long id;
    private String comContent;
    private Long comRating;
    private Long userId;
    private Long productId;
    private Long transactionId;
}
