package com.example.clothingstore.model;

import javax.persistence.*;


@Entity
@Table(name = "commune")
public class CommuneEntity {
    @Id
    private Long id;
    private String commune;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private DistrictEntity districtEntity;

    // Constructor, Getter and Setter
    public CommuneEntity() {
    }

    public CommuneEntity(Long id, String commune, DistrictEntity districtEntity) {
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

    public DistrictEntity getDistrictEntity() {
        return districtEntity;
    }

    public void setDistrictEntity(DistrictEntity districtEntity) {
        this.districtEntity = districtEntity;
    }
}
