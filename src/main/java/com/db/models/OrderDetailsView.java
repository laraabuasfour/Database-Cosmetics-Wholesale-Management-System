package com.db.models;

import java.math.BigDecimal;

public class OrderDetailsView {

    private int productNum;
    private String productName;
    private BigDecimal sellingPrice;
    private int quantity;

    public OrderDetailsView(int productNum, String productName, BigDecimal sellingPrice, int quantity) {
        this.productNum = productNum;
        this.productName = productName;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
    }

    public int getProductNum() {
        return productNum;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
