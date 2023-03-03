package com.example.clothingstore.config.mapper;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class CommentMapper {
    private Long id;
    private String comContent;
    private Long comRating;
    private String userFullName;
    private String avatar;
    @JsonFormat(timezone="Asia/Jakarta", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp create_at;


    public CommentMapper(Long id, String comContent, Long comRating, String userFullName) {
        this.id = id;
        this.comContent = comContent;
        this.comRating = comRating;
        this.userFullName = userFullName;
    }

    public CommentMapper(Long id, String comContent, Long comRating, String userFullName, Timestamp create_at) {
        this.id = id;
        this.comContent = comContent;
        this.comRating = comRating;
        this.userFullName = userFullName;
        this.create_at = create_at;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Timestamp getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Timestamp create_at) {
        this.create_at = create_at;
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
