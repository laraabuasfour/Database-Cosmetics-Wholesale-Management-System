package com.db.models;

public class Store {
    private String storeName;     // Primary Key
    private String phoneNumber;
    private String location;

    public Store(String storeName, String phoneNumber, String location) {
        this.storeName = storeName;
        this.phoneNumber = phoneNumber;
        this.location = location;
    }

    public Store() {
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}