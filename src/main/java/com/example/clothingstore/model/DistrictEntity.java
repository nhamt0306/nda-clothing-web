package com.example.clothingstore.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "district")
public class DistrictEntity {
    @Id
    private Long id;
    private String district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id")
    private ProvinceEntity provinceEntity;

    @OneToMany(mappedBy = "commune", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommuneEntity> communeEntities = new ArrayList<>();

    // Constructor, Getter and Setter
    public DistrictEntity() {
    }

    public DistrictEntity(Long id, String district, ProvinceEntity provinceEntity) {
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

    public ProvinceEntity getProvinceEntity() {
        return provinceEntity;
    }

    public void setProvinceEntity(ProvinceEntity provinceEntity) {
        this.provinceEntity = provinceEntity;
    }
}
