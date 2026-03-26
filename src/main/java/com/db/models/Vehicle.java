package com.db.models;

public class Vehicle {
    private String permitNumber;      // Primary Key
    private int capacity;
    private String vehicleNumber;
    private String phoneNumber;
    private int employeeId;

    public Vehicle() {
    }

    public Vehicle(String permitNumber, int capacity, String vehicleNumber, String phoneNumber, int employeeId) {
        this.permitNumber = permitNumber;
        this.capacity = capacity;
        this.vehicleNumber = vehicleNumber;
        this.phoneNumber = phoneNumber;
        this.employeeId = employeeId;
    }

    public String getPermitNumber() {
        return permitNumber;
    }

    public void setPermitNumber(String permitNumber) {
        this.permitNumber = permitNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

}