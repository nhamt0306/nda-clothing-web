package com.example.clothingstore.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "transactions")
public class TransactionEntity extends BaseClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long unitPrice;
    private Long quantity;
    private String color;
    private Long size;

    //  Relationship with table Product
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "productId", nullable = false, referencedColumnName = "id")
    private ProductEntity productEntity;

    //  Relationship with table Order
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "orderId", nullable = false, referencedColumnName = "id")
    private OrderEntity orderEntity;


    // Constructor
    public TransactionEntity() {
    }


    public TransactionEntity(Long id, Long unitPrice, Long quantity, String color, Long size) {
        this.id = id;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.color = color;
        this.size = size;
    }

    public TransactionEntity(Long unitPrice, Long quantity, String color, Long size, ProductEntity productEntity) {
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.color = color;
        this.size = size;
        this.productEntity = productEntity;
    }

    public TransactionEntity(Long unitPrice, Long quantity, String color, Long size) {
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.color = color;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }
}
