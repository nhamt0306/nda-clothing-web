package com.example.clothingstore.mapper;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.util.List;

public class OrderMapper {
    private Long id;
    private Long ordTotalPrice;
    private String ordNote;
    private Long ordShippingFee;
    private String ordPayment;
    private String ordStatus;
    private String ordAddress;
    private String ordPhone;

    private String ordName;
    @JsonFormat(timezone = "Asia/Jakarta", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp ordDate;
    private List<TransactionMapper> transactionMapper;

    public String getOrdName() {
        return ordName;
    }

    public void setOrdName(String ordName) {
        this.ordName = ordName;
    }

    public OrderMapper(Long id, Long ordTotalPrice, String ordNote, Long ordShippingFee, String ordPayment, String ordStatus, String ordAddress, String ordPhone, Timestamp ordDate, String ordName) {
        this.id = id;
        this.ordTotalPrice = ordTotalPrice;
        this.ordNote = ordNote;
        this.ordShippingFee = ordShippingFee;
        this.ordPayment = ordPayment;
        this.ordStatus = ordStatus;
        this.ordAddress = ordAddress;
        this.ordPhone = ordPhone;
        this.ordDate = ordDate;
        this.ordName = ordName;
    }

    public OrderMapper(Long id, Long ordTotalPrice, String ordNote, Long ordShippingFee, String ordPayment, String ordStatus, String ordAddress, String ordPhone, Timestamp ordDate) {
        this.id = id;
        this.ordTotalPrice = ordTotalPrice;
        this.ordNote = ordNote;
        this.ordShippingFee = ordShippingFee;
        this.ordPayment = ordPayment;
        this.ordStatus = ordStatus;
        this.ordAddress = ordAddress;
        this.ordPhone = ordPhone;
        this.ordDate = ordDate;
    }

    public List<TransactionMapper> getTransactionMapper() {
        return transactionMapper;
    }

    public void setTransactionMapper(List<TransactionMapper> transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrdTotalPrice() {
        return ordTotalPrice;
    }

    public void setOrdTotalPrice(Long ordTotalPrice) {
        this.ordTotalPrice = ordTotalPrice;
    }

    public String getOrdNote() {
        return ordNote;
    }

    public void setOrdNote(String ordNote) {
        this.ordNote = ordNote;
    }

    public Long getOrdShippingFee() {
        return ordShippingFee;
    }

    public void setOrdShippingFee(Long ordShippingFee) {
        this.ordShippingFee = ordShippingFee;
    }

    public String getOrdPayment() {
        return ordPayment;
    }

    public void setOrdPayment(String ordPayment) {
        this.ordPayment = ordPayment;
    }

    public String getOrdStatus() {
        return ordStatus;
    }

    public void setOrdStatus(String ordStatus) {
        this.ordStatus = ordStatus;
    }

    public String getOrdAddress() {
        return ordAddress;
    }

    public void setOrdAddress(String ordAddress) {
        this.ordAddress = ordAddress;
    }

    public String getOrdPhone() {
        return ordPhone;
    }

    public void setOrdPhone(String ordPhone) {
        this.ordPhone = ordPhone;
    }

    public Timestamp getOrdDate() {
        return ordDate;
    }

    public void setOrdDate(Timestamp ordDate) {
        this.ordDate = ordDate;
    }
}
