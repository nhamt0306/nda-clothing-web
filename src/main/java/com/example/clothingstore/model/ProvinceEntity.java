package com.example.clothingstore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "province")
public class ProvinceEntity {
    @Id
    private Long id;
    private String province;

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    private List<DistrictEntity> districtEntities = new ArrayList<>();

    // Constructor, Getter and Setter
    public ProvinceEntity() {
    }

    public ProvinceEntity(Long id, String province) {
        this.id = id;
        this.province = province;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
