package com.example.clothingstore.mapper;

public class CommentMapper {
    private Long id;
    private String comContent;
    private Long comRating;
    private String userFullName;

    public CommentMapper(Long id, String comContent, Long comRating, String userFullName) {
        this.id = id;
        this.comContent = comContent;
        this.comRating = comRating;
        this.userFullName = userFullName;
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

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }
}
