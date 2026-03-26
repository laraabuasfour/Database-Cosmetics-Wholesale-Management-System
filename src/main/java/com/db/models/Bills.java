package com.db.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Bills {

    private int billId;
    private LocalDate dateOfTransaction;
    private String type;
    private BigDecimal totalPrice;
    private int orderId; //TODO:connect table

    // Constructor with all fields
    public Bills(int billId, LocalDate dateOfTransaction, String type, BigDecimal totalPrice, int orderId) {
        this.billId = billId;
        this.dateOfTransaction = dateOfTransaction;
        this.type = type;
        this.totalPrice = totalPrice;
        this.orderId = orderId;
    }

    // No-args constructor
    public Bills() {
    }

    // Getters
    public int getBillId() {
        return billId;
    }

    public LocalDate getDateOfTransaction() {
        return dateOfTransaction;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    // Setters
    public void setBillId(int billId) {
        this.billId = billId;
    }

    public void setDateOfTransaction(LocalDate dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
