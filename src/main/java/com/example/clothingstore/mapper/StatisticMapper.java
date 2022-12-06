package com.example.clothingstore.mapper;

public class StatisticMapper {
    private String date;
    private Integer profit;

    public StatisticMapper(String date, Integer profit) {
        this.date = date;
        this.profit = profit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getProfit() {
        return profit;
    }

    public void setProfit(Integer profit) {
        this.profit = profit;
    }
}
