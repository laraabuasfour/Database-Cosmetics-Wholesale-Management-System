package com.db.models;

import java.math.BigDecimal;

public class ProductComboItem {
    private final int productId;
    private final String productName;
    private final BigDecimal sellingPrice;

    public ProductComboItem(int productId, String productName, BigDecimal sellingPrice) {
        this.productId = productId;
        this.productName = productName;
        this.sellingPrice = sellingPrice;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    @Override
    public String toString() {
        return productId + " - " + productName;
    }
}
