package com.example.clothingstore.config.mapper;

public class DistrictMapper {
    private Long id;

    private String district;

    public DistrictMapper(Long id, String district) {
        this.id = id;
        this.district = district;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
