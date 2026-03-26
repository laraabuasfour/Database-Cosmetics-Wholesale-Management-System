package com.db.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductDiscountView {
    private int pN;
    private String name;
    private int totalQuantity;
    private BigDecimal originalPrice;
    private BigDecimal discountedPrice;
    private LocalDate expiringDate;

    // Constructor
    public ProductDiscountView(int pN, String name, int totalQuantity, BigDecimal originalPrice, BigDecimal discountedPrice, LocalDate expiringDate) {
        this.pN = pN;
        this.name = name;
        this.totalQuantity = totalQuantity;
        this.originalPrice = originalPrice;
        this.discountedPrice = discountedPrice;
        this.expiringDate = expiringDate;
    }

    // Constructor without expiring date (for backward compatibility)
    public ProductDiscountView(int pN, String name, int totalQuantity, BigDecimal originalPrice, BigDecimal discountedPrice) {
        this.pN = pN;
        this.name = name;
        this.totalQuantity = totalQuantity;
        this.originalPrice = originalPrice;
        this.discountedPrice = discountedPrice;
    }

    public ProductDiscountView() {
    }

    // Getters
    public int getPN() {
        return pN;
    }

    public String getName() {
        return name;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    public LocalDate getExpiringDate() {
        return expiringDate;
    }

    // Setters
    public void setPN(int pN) {
        this.pN = pN;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public void setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public void setExpiringDate(LocalDate expiringDate) {
        this.expiringDate = expiringDate;
    }
}
