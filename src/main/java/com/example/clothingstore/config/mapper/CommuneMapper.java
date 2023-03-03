package com.example.clothingstore.config.mapper;

public class CommuneMapper {
    private Long id;

    private String commune;

    public CommuneMapper(Long id, String commune) {
        this.id = id;
        this.commune = commune;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }
}
