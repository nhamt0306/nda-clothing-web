package com.example.clothingstore.config.mapper;

import java.util.ArrayList;
import java.util.List;

public class OrderPagingResponse {
    List<OrderMapper> orderMapperList = new ArrayList<>();
    int numberItem;

    public OrderPagingResponse(List<OrderMapper> orderMapperList, int numberItem) {
        this.orderMapperList = orderMapperList;
        this.numberItem = numberItem;
    }

    public List<OrderMapper> getOrderMapperList() {
        return orderMapperList;
    }

    public void setOrderMapperList(List<OrderMapper> orderMapperList) {
        this.orderMapperList = orderMapperList;
    }

    public int getNumberItem() {
        return numberItem;
    }

    public void setNumberItem(int numberItem) {
        this.numberItem = numberItem;
    }
}
