package com.example.clothingstore.dto;

public class BadRequest extends RuntimeException{
    public BadRequest(String message){
        super(message);
    }
}
