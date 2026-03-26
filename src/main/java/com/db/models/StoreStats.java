package com.db.models;

public class StoreStats {
    private String location;
    private int totalStores;

    public StoreStats(String location, int totalStores) {
        this.location = location;
        this.totalStores = totalStores;
    }

    public String getLocation() {
        return location;
    }

    public int getTotalStores() {
        return totalStores;
    }
}