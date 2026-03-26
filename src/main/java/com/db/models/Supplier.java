package com.db.models;

public class Supplier {

    private int supplierId;
    private String name;
    private String location;
    private String phoneNumber;

    public Supplier(int supplierId, String name, String location, String phoneNumber) {
        this.supplierId = supplierId;
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
    }

    public Supplier() {
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
