package com.example.clothingstore.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "carts_products")
public class CartProductEntity extends BaseClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long quantity;
    private Long price;
    private String color;
    private Long size;

    //  Relationship with table Cart
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "cartId", nullable = false, referencedColumnName = "id")
    //cartId is foreign key of SubCategory and referenced to id of table CartEntity
    private CartEntity cartEntity;

    //  Relationship with table Product
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "productId", nullable = false, referencedColumnName = "id")
    //productId is foreign key of SubCategory and referenced to id of table ProductEntity
    private ProductEntity productEntity;

    //Constructor
    public CartProductEntity() {
    }

    public CartProductEntity(Long id, Long quantity) {
        this.id = id;
        this.quantity = quantity;
    }


    public CartProductEntity(Long id, Long quantity, Long price, String color, Long size) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.color = color;
        this.size = size;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public CartEntity getCartEntity() {
        return cartEntity;
    }

    public void setCartEntity(CartEntity cartEntity) {
        this.cartEntity = cartEntity;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
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
}
