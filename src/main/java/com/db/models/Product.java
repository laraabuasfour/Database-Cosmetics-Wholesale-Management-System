package com.db.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Product {

    private int productId;
    private String name;
    private BigDecimal sellingPrice;
    private BigDecimal buyingPrice;
    private int quantity;
    private LocalDate expiringDate;
    private int orderId;  //TODO: import order table

    public Product(int productId, String name, BigDecimal sellingPrice, BigDecimal buyingPrice,
                   int quantity, LocalDate expiringDate, int orderId) {
        this.productId = productId;
        this.name = name;
        this.sellingPrice = sellingPrice;
        this.buyingPrice = buyingPrice;
        this.quantity = quantity;
        this.expiringDate = expiringDate;
        this.orderId = orderId;
    }

    public Product() {
    }


    // Getters
    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getExpiringDate() {
        return expiringDate;
    }

    public int getOrderId() {
        return orderId;
    }

    // Setters
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setExpiringDate(LocalDate expiringDate) {
        this.expiringDate = expiringDate;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }






}
