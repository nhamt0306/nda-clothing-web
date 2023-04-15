package com.example.clothingstore.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "types")
public class TypeEntity extends BaseClassEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long quantity;//number product now
    private Long price;
    private Long size;
    private String color;
    private Long sale;
    private Long importPrice;
    private Long importQuantity;
    private Long sold; //number sold
    private String status = "Active";

    // Relationship with table ProductEntity
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "productId", nullable = false, referencedColumnName = "id")
    private ProductEntity productEntity;


    //Constructor

    public TypeEntity() {
    }

    public TypeEntity(Long id, Long quantity, Long price, Long size, String color, String s) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.size = size;
        this.color = color;
        this.status = s;
    }

    public TypeEntity(Long id, Long quantity, Long price, Long size, String color, Long sale, Long sold, String status) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.size = size;
        this.color = color;
        this.sale = sale;
        this.sold = sold;
        this.status = status;
    }

    public Long getImportQuantity() {
        return importQuantity;
    }

    public void setImportQuantity(Long importQuantity) {
        this.importQuantity = importQuantity;
    }

    public Long getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(Long importPrice) {
        this.importPrice = importPrice;
    }

    public Long getSale() {
        return sale;
    }

    public void setSale(Long sale) {
        this.sale = sale;
    }

    public Long getSold() {
        return sold;
    }

    public void setSold(Long sold) {
        this.sold = sold;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
