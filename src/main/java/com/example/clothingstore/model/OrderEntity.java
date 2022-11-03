package com.example.clothingstore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity extends BaseClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long totalPrice;
    private String note;
    private Long shippingFee;
    private String status;
    private String payment;
    private String name;
    private String address;
    private String phone;

    // Relationship with table OrderDetailsEntity
    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TransactionEntity> orderDetailsEntities;

    // Relationship with table UserEntity (n:1). Because 1 user can have n orders
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userId", nullable = false, referencedColumnName = "id")
    @JsonBackReference
    //category_id is foreign key of SubCategory and referenced to id of table Category
    private UserEntity userEntity; //Relative with MapBy in CategoryEntity


    // Constructor
    public OrderEntity() {
    }

    public OrderEntity(Long id, Long totalPrice, String note, Long shippingFee, String status, String payment) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.note = note;
        this.shippingFee = shippingFee;
        this.status = status;
        this.payment = payment;
    }

    public OrderEntity(Long totalPrice, String note, Long shippingFee, String status, String payment, List<TransactionEntity> orderDetailsEntities, UserEntity userEntity) {
        this.totalPrice = totalPrice;
        this.note = note;
        this.shippingFee = shippingFee;
        this.status = status;
        this.payment = payment;
        this.orderDetailsEntities = orderDetailsEntities;
        this.userEntity = userEntity;
    }

    public OrderEntity(Long totalPrice, String note, Long shippingFee, String status, String payment, String name, String address, String phone) {
        this.totalPrice = totalPrice;
        this.note = note;
        this.shippingFee = shippingFee;
        this.status = status;
        this.payment = payment;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<TransactionEntity> getOrderDetailsEntities() {
        return orderDetailsEntities;
    }

    public void setOrderDetailsEntities(List<TransactionEntity> orderDetailsEntities) {
        this.orderDetailsEntities = orderDetailsEntities;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Long shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
