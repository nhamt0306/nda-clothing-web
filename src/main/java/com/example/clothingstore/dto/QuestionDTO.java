package com.example.clothingstore.dto;

public class QuestionDTO {
    private String question;

    public QuestionDTO(String question) {
        this.question = question;
    }

    public QuestionDTO() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
