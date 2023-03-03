package com.example.clothingstore.config.mapper;

public class StatisticMapper {
    private String time;
    private Integer amount;

    public StatisticMapper(String time, Integer amount) {
        this.time = time;
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
