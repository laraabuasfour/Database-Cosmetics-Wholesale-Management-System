package com.db.controllers;

import com.db.database.DbHelper;
import com.db.models.Employee;
import com.db.models.Orders;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class OrdersInfoSaveController {

    // Inner class to represent employee in ComboBox
    public static class EmployeeComboItem {
        private final int employeeId;
        private final String employeeName;

        public EmployeeComboItem(int employeeId, String employeeName) {
            this.employeeId = employeeId;
            this.employeeName = employeeName;
        }

        public int getEmployeeId() {
            return employeeId;
        }

        public String getEmployeeName() {
            return employeeName;
        }

        @Override
        public String toString() {
            return employeeId + " - " + employeeName;
        }
    }

    @FXML
    private ComboBox<String> storeComboBox;

    @FXML
    private ComboBox<EmployeeComboItem> employeeComboBox;

    @FXML
    private DatePicker orderDate;

    @FXML
    private Button saveBtn;

    @FXML
    private Label errorLabel;

    @FXML
    private Button closeBtn;

    private Orders orderToUpdate;
    private boolean isUpdate = false;
    private Runnable callback;

    @FXML
    public void initialize() {
        loadEmployees();
        loadStores();
    }

    private void loadEmployees() {
        try {
            List<Employee> employees = DbHelper.getAllEmployees();
            for (Employee employee : employees) {
                EmployeeComboItem item = new EmployeeComboItem(
                    employee.getEmployeeId(), 
                    employee.getEname()
                );
                employeeComboBox.getItems().add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Error loading employees: " + e.getMessage());
        }
    }

    private void loadStores() {
        try {
            List<String> stores = DbHelper.getAllStoreNames();
            storeComboBox.getItems().addAll(stores);
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Error loading stores: " + e.getMessage());
        }
    }

    @FXML
    void save(ActionEvent event) {
        saveBtn.setDisable(true);
        closeBtn.setDisable(true);

        String store = storeComboBox.getValue();
        EmployeeComboItem selectedEmployee = employeeComboBox.getValue();
        LocalDate date = orderDate.getValue();

        if (!validateData(store, selectedEmployee, date)) {
            saveBtn.setDisable(false);
            closeBtn.setDisable(false);
            return;
        }

        int empId = selectedEmployee.getEmployeeId();

        if (isUpdate) {
            orderToUpdate.setStoreName(store);
            orderToUpdate.setEmployeeId(empId);
            orderToUpdate.setOrderDate(date);
            DbHelper.updateOrder(orderToUpdate);
        } else {
            Orders newOrder = new Orders();
            newOrder.setOrderId(DbHelper.getNextOrderId());
            newOrder.setStoreName(store);
            newOrder.setEmployeeId(empId);
            newOrder.setOrderDate(date);
            DbHelper.addOrder(newOrder);
        }

        if (callback != null) {
            callback.run();
        }

        Stage stage = (Stage) saveBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void closeClicked(ActionEvent event) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    public void setOrderToUpdate(Orders orderToUpdate) {
        this.orderToUpdate = orderToUpdate;
        if (orderToUpdate != null) {
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
        storeComboBox.setValue(orderToUpdate.getStoreName());
        
        // Find and select the correct employee in the ComboBox
        int employeeIdToSelect = orderToUpdate.getEmployeeId();
        for (EmployeeComboItem item : employeeComboBox.getItems()) {
            if (item.getEmployeeId() == employeeIdToSelect) {
                employeeComboBox.setValue(item);
                break;
            }
        }
        
        orderDate.setValue(orderToUpdate.getOrderDate());
    }

    private boolean validateData(String store, EmployeeComboItem selectedEmployee, LocalDate date) {
        if (store == null || store.isEmpty()) {
            errorLabel.setText("Store selection is required");
            return false;
        } else if (selectedEmployee == null) {
            errorLabel.setText("Employee selection is required");
            return false;
        }

        if (date == null) {
            errorLabel.setText("Order date is required");
            return false;
        }

        return true;
    }
}
