package com.example.clothingstore.config.mapper;

import java.util.ArrayList;
import java.util.List;

public class ProductPagingResponse {
    List<ProductMapper> list = new ArrayList<>();
    long numberItem;

    public ProductPagingResponse(List<ProductMapper> list, long numberItem) {
        this.list = list;
        this.numberItem = numberItem;
    }

    public List<ProductMapper> getList() {
        return list;
    }

    public void setList(List<ProductMapper> list) {
        this.list = list;
    }

    public long getNumberItem() {
        return numberItem;
    }

    public void setNumberItem(int numberItem) {
        this.numberItem = numberItem;
    }
}
