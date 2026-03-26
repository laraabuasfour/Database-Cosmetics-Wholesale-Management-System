package com.db.models;

import java.time.LocalDate;

public class Orders {

    private int orderId;
    private String storeName; //TODO: connect table
    private int employeeId; //TODO: connect table
    private String employeeName; // Employee name for display purposes
    private LocalDate orderDate;

    // Constructor with all fields
    public Orders(int orderId, String storeName, int employeeId, LocalDate orderDate) {
        this.orderId = orderId;
        this.storeName = storeName;
        this.employeeId = employeeId;
        this.orderDate = orderDate;
    }

    // Constructor with employee name
    public Orders(int orderId, String storeName, int employeeId, String employeeName, LocalDate orderDate) {
        this.orderId = orderId;
        this.storeName = storeName;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.orderDate = orderDate;
    }

    // No-args constructor
    public Orders() {
    }

    // Getters
    public int getOrderId() {
        return orderId;
    }

    public String getStoreName() {
        return storeName;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    // Setters
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
}
