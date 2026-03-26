package com.db.controllers;

import com.db.database.DbHelper;
import com.db.models.Warehouse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class WarehouseTableController {
    @FXML
    private TableView<Warehouse> warehouseTable;

    @FXML
    private TableColumn<Warehouse, Integer> warehouseid;

    @FXML
    private TableColumn<Warehouse, String> warehouse_location;

    @FXML
    private TableColumn<Warehouse, Integer> warehouse_capacity;

    @FXML
    private TableColumn<Warehouse, Void> actions;

    @FXML
    private Button warehousebut;

    private ObservableList<Warehouse> warehouseList = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws Exception {
        warehouseid.setCellValueFactory(new PropertyValueFactory<>("warehouseId"));
        warehouse_location.setCellValueFactory(new PropertyValueFactory<>("location"));
        warehouse_capacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        actions.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox box = new HBox(5, editButton, deleteButton);

            {
                box.setAlignment(Pos.CENTER);

                editButton.setOnAction(e -> {
                    Warehouse warehouse = getTableView().getItems().get(getIndex());
                    openCreateUpdateScene(true, warehouse);
                });

                deleteButton.setOnAction(e -> {
                    Warehouse warehouse = getTableView().getItems().get(getIndex());
                    showDeleteConfirmationDialog(warehouse);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty || getIndex() >= getTableView().getItems().size() ? null : box);
            }
        });

        warehouseTable.setItems(warehouseList);
        refreshTable();
    }

    @FXML
    void onClick(ActionEvent event) throws Exception {
        openCreateUpdateScene(false, null);
    }

    private void refreshTable() throws Exception {
        warehouseList.clear();
        List<Warehouse> warehouses = DbHelper.getAllWarehouses();
        warehouseList.setAll(warehouses);
    }

    private void openCreateUpdateScene(boolean isUpdate, Warehouse warehouse) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WarehouseCreateUpdate.fxml"));
            Parent root = loader.load();

            WarehouseInfoSaveController controller = loader.getController();
            controller.setUpdate(isUpdate);
            if (isUpdate) {
                controller.setWarehouseToUpdate(warehouse);
            }
            controller.setCallbackFunction(() -> {
                try {
                    refreshTable();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(isUpdate ? "Edit Warehouse" : "Add New Warehouse");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showDeleteConfirmationDialog(Warehouse warehouse) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Warehouse");
        alert.setHeaderText("Are you sure you want to delete this warehouse?");
        alert.setContentText("Warehouse ID: " + warehouse.getWarehouseId());

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                deleteWarehouse(warehouse);
            }
        });
    }

    private void deleteWarehouse(Warehouse warehouse) {
        try {
            DbHelper.deleteWarehouse(warehouse.getWarehouseId());
            refreshTable();
            System.out.println("Warehouse deleted: " + warehouse.getWarehouseId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Q1
    @FXML
    void showChart(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WarehouseChart.fxml"));
            Parent chartRoot = loader.load();

            Stage chartStage = new Stage();
            chartStage.setTitle("Warehouse Chart");
            chartStage.setScene(new Scene(chartRoot));
            chartStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Q2
    @FXML
    void showTop5Warehouses(ActionEvent event) {
        try {
            List<Warehouse> topList = DbHelper.getTop5WarehousesByCapacity();
            warehouseTable.getItems().clear();
            warehouseTable.getItems().addAll(topList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}