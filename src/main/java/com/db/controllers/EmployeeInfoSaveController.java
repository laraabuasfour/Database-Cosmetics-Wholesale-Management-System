package com.db.controllers;

import com.db.database.DbHelper;
import com.db.models.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeInfoSaveController {

    @FXML
    private DatePicker birthDate;

    @FXML
    private Button closeButton;

    @FXML
    private TextField name;

    @FXML
    private TextField phoneNum;

    @FXML
    private TextField role;

    @FXML
    private TextField salary;

    @FXML
    private Button saveButton;

    @FXML
    private Label errorLabel;

    private Employee employeeToUpdate;
    private boolean isUpdate = false;
    private Runnable callback;

    @FXML
    void save(ActionEvent event) {
        saveButton.setDisable(true);
        closeButton.setDisable(true);

        String nameValue = name.getText();
        String phoneValue = phoneNum.getText();
        String roleValue = role.getText();
        String salaryValue = salary.getText();
        LocalDate birthDateValue = birthDate.getValue();

        if (!validateData(nameValue, phoneValue, roleValue, salaryValue, birthDateValue)) {
            saveButton.setDisable(false);
            closeButton.setDisable(false);
            return;
        }

        try {
            BigDecimal salaryDecimal = new BigDecimal(salaryValue);
            LocalDate birthDateParsed = birthDateValue;

            if (isUpdate) {
                employeeToUpdate.setEname(nameValue);
                employeeToUpdate.setPhoneNumber(phoneValue);
                employeeToUpdate.setRole(roleValue);
                employeeToUpdate.setSalary(salaryDecimal);
                employeeToUpdate.setBirthDate(birthDateParsed);
                DbHelper.updateEmployee(employeeToUpdate);
            } else {
                Employee employee = new Employee();
                employee.setEmployeeId(DbHelper.getNextEmployeeId());
                employee.setEname(nameValue);
                employee.setPhoneNumber(phoneValue);
                employee.setRole(roleValue);
                employee.setSalary(salaryDecimal);
                employee.setBirthDate(birthDateParsed);
                DbHelper.addEmployee(employee);
            }

            if (callback != null) {
                callback.run();
            }

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            errorLabel.setText("Invalid input: " + e.getMessage());
            saveButton.setDisable(false);
            closeButton.setDisable(false);
        }
    }

    @FXML
    void closeClicked(ActionEvent event) {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    public void setEmployeeToUpdate(Employee employeeToUpdate) {
        this.employeeToUpdate = employeeToUpdate;
        if (employeeToUpdate != null) {
            populateFields();
        }
    }

    public void setUpdate(boolean update) {
        this.isUpdate = update;
    }

    public void setCallbackFunction(Runnable runnable) {
        this.callback = runnable;
    }

    private void populateFields() {
        name.setText(employeeToUpdate.getEname());
        phoneNum.setText(employeeToUpdate.getPhoneNumber());
        role.setText(employeeToUpdate.getRole());
        salary.setText(employeeToUpdate.getSalary().toString());
        birthDate.setValue(employeeToUpdate.getBirthDate());
    }

    private boolean validateData(String nameValue, String phoneValue, String roleValue,
                                 String salaryValue, LocalDate birthDateValue) {
        if (nameValue == null || nameValue.isEmpty()) {
            errorLabel.setText("Name is required");
            return false;
        } else if (phoneValue == null || phoneValue.isEmpty()) {
            errorLabel.setText("Phone Number is required");
            return false;
        } else if (roleValue == null || roleValue.isEmpty()) {
            errorLabel.setText("Role is required");
            return false;
        } else if (salaryValue == null || salaryValue.isEmpty()) {
            errorLabel.setText("Salary is required");
            return false;
        } else if (birthDateValue == null) {
            errorLabel.setText("Birth Date is required");
            return false;
        }
        return true;
    }
}
