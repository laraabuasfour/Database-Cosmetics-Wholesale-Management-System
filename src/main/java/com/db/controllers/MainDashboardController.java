package com.db.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainDashboardController {

    @FXML
    private Button employeesBtn;

    @FXML
    private Button productsBtn;

    @FXML
    private Button ordersBtn;

    @FXML
    private Button suppliersBtn;

    @FXML
    private Button billsBtn;

    @FXML
    private Button discountedProductsBtn;

    @FXML
    private Button warehousesBtn;

    @FXML
    private Button vehiclesBtn;

    @FXML
    private Button storesBtn;

    @FXML
    void openEmployeesTable(ActionEvent event) {
        openNewWindow("/EmployeeTable.fxml", "Employee Management");
    }

    @FXML
    void openProductsTable(ActionEvent event) {
        openNewWindow("/ProductTable.fxml", "Product Management");
    }

    @FXML
    void openOrdersTable(ActionEvent event) {
        openNewWindow("/OrderTable.fxml", "Order Management");
    }

    @FXML
    void openSuppliersTable(ActionEvent event) {
        openNewWindow("/SupplierTable.fxml", "Supplier Management");
    }

    @FXML
    void openBillsTable(ActionEvent event) {
        openNewWindow("/BillsTable.fxml", "Bills Management");
    }

    @FXML
    void openDiscountedProductsTable(ActionEvent event) {
        openNewWindow("/DiscountedProductsTable.fxml", "Discounted Products");
    }

    @FXML
    void openWarehousesTable(ActionEvent event) {
        openNewWindow("/WarehouseTable.fxml", "Warehouse Management");
    }

    @FXML
    void openVehiclesTable(ActionEvent event) {
        openNewWindow("/VehicleTable.fxml", "Vehicle Management");
    }

    @FXML
    void openStoresTable(ActionEvent event) {
        openNewWindow("/StoreTable.fxml", "Store Management");
    }

    private void openNewWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error opening " + title + " window: " + e.getMessage());
        }
    }
}