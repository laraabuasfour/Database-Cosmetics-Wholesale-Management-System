package com.db.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee {

    private int employeeId;
    private String ename;
    private BigDecimal salary;
    private String phoneNumber;
    private LocalDate birthDate;
    private String role;

    // Constructor with all fields
    public Employee(int employeeId, String ename, BigDecimal salary, String phoneNumber,
                    LocalDate birthDate, String role) {
        this.employeeId = employeeId;
        this.ename = ename;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.role = role;
    }


    // No-args constructor
    public Employee() {
    }

    // Getters
    public int getEmployeeId() {
        return employeeId;
    }

    public String getEname() {
        return ename;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getRole() {
        return role;
    }

    // Setters
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
