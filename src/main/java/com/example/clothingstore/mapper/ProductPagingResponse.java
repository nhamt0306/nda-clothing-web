package com.example.clothingstore.mapper;

import java.util.ArrayList;
import java.util.List;

public class ProductPagingResponse {
    List<ProductMapper> list = new ArrayList<>();
    int numberItem;

    public ProductPagingResponse(List<ProductMapper> list, int numberItem) {
        this.list = list;
        this.numberItem = numberItem;
    }

    public List<ProductMapper> getList() {
        return list;
    }

    public void setList(List<ProductMapper> list) {
        this.list = list;
    }

    public int getNumberItem() {
        return numberItem;
    }

    public void setNumberItem(int numberItem) {
        this.numberItem = numberItem;
    }
}
