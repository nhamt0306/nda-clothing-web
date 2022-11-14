package com.example.clothingstore.dto;

import java.util.List;

public class ColorAndTypeDTO {
    private Long productId;
    private List<String> colorList;
    private List<Long> sizeList;

    public ColorAndTypeDTO(List<String> colorList, List<Long> sizeList) {
        this.colorList = colorList;
        this.sizeList = sizeList;
    }

    public ColorAndTypeDTO(Long productId, List<String> colorList, List<Long> sizeList) {
        this.productId = productId;
        this.colorList = colorList;
        this.sizeList = sizeList;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<String> getColorList() {
        return colorList;
    }

    public void setColorList(List<String> colorList) {
        this.colorList = colorList;
    }

    public List<Long> getSizeList() {
        return sizeList;
    }

    public void setSizeList(List<Long> sizeList) {
        this.sizeList = sizeList;
    }
}
