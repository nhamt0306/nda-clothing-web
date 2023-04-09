package com.example.clothingstore.config.mapper;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class CommentPagingResponse {
    private List<CommentMapper> list = new ArrayList<>();
    private Integer numberItem;

    public CommentPagingResponse(List<CommentMapper> list, Integer numberItem) {
        this.list = list;
        this.numberItem = numberItem;
    }
}
