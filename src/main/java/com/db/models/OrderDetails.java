package com.db.models;

public class OrderDetails {

    private int id;
    private int orderId;
    private int pn; // Assuming PN = Product Number or Product ID
    private int quantity;

    // Constructor with all fields
    public OrderDetails(int id, int orderId, int pn, int quantity) {
        this.id = id;
        this.orderId = orderId;
        this.pn = pn;
        this.quantity = quantity;
    }

    // No-args constructor
    public OrderDetails() {
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getPn() {
        return pn;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setPn(int pn) {
        this.pn = pn;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
