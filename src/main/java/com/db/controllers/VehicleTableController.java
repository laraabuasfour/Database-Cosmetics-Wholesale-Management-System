package com.db.controllers;

import com.db.database.DbHelper;
import com.db.models.Vehicle;
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
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.List;

public class VehicleTableController {
    @FXML
    private Button addButton;
    @FXML
    private TableColumn<Vehicle, String> permit_number;

    @FXML
    private TableColumn<Vehicle, Integer> vcapacity;

    @FXML
    private TableColumn<Vehicle, String> vnum;

    @FXML
    private TableColumn<Vehicle, String> phonenum;

    @FXML
    private TableColumn<Vehicle, Integer> eid;

    @FXML
    private TableColumn<Vehicle, Void> actions;

    @FXML
    private TableView<Vehicle> vehicleTable;
    //q2
    @FXML
    private TextField searchField;

    @FXML //q2
    private Button searchBtn;

    @FXML //q3
    private Label vehicleStatsLabel;

    private void updateVehicleCount() { //q3
        int total = DbHelper.countVehicles();
        vehicleStatsLabel.setText("Total Vehicles: " + total);
    }


    private ObservableList<Vehicle> vehicleList = FXCollections.observableArrayList();
//q2
    @FXML
    void onSearchClicked(ActionEvent event) {
        String permit = searchField.getText().trim();
        if (permit.isEmpty()) {
            try {
                refreshTable();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        try {
            Vehicle found = DbHelper.getVehicleByPermit(permit);
            vehicleList.clear();
            if (found != null) {
                vehicleList.add(found);
            } else {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Result");
                alert.setHeaderText(null);
                alert.setContentText("No vehicle found with permit number: " + permit);
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
}

        @FXML
    public void initialize() throws Exception {
            updateVehicleCount(); //q3
            permit_number.setCellValueFactory(new PropertyValueFactory<>("permitNumber"));
        vcapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        vnum.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        phonenum.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        eid.setCellValueFactory(new PropertyValueFactory<>("employeeId"));

        actions.setCellFactory(col -> new TableCell<Vehicle, Void>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox box = new HBox(5, editButton, deleteButton);

            {
                box.setAlignment(Pos.CENTER);

                editButton.setOnAction(e -> {
                    Vehicle vehicle = getTableView().getItems().get(getIndex());
                    openCreateUpdateScene(true, vehicle);
                });

                deleteButton.setOnAction(e -> {
                    Vehicle vehicle = getTableView().getItems().get(getIndex());
                    showDeleteConfirmationDialog(vehicle);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                } else {
                    setGraphic(box);
                }
            }
        });

        vehicleTable.setItems(vehicleList);
        refreshTable();
    }

    @FXML
    void onClick(ActionEvent event) throws Exception {
        openCreateUpdateScene(false, null);
    }

    private void refreshTable() throws Exception {
        vehicleList.clear();
        List<Vehicle> vehicles = DbHelper.getAllVehicles(); // You must implement this in DbHelper
        vehicleList.setAll(vehicles);
        updateVehicleCount(); //q3


    }

    private void openCreateUpdateScene(boolean isUpdate, Vehicle vehicle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VehicleCreateUpdate.fxml"));
            Parent root = loader.load();

            VehicleInfoSaveController controller = loader.getController(); // You'll create this class
            controller.setUpdate(isUpdate);
            if (isUpdate) {
                controller.setVehicleToUpdate(vehicle);
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
            stage.setTitle(isUpdate ? "Edit Vehicle" : "Add New Vehicle");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showDeleteConfirmationDialog(Vehicle vehicle) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Vehicle");
        alert.setHeaderText("Are you sure you want to delete this vehicle?");
        alert.setContentText("Vehicle Permit: " + vehicle.getPermitNumber() + " will be deleted.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                deleteVehicle(vehicle);
            }
        });
    }

    private void deleteVehicle(Vehicle vehicle) {
        try {
            DbHelper.deleteVehicle(vehicle.getPermitNumber()); // You must implement this in DbHelper
            refreshTable();
            System.out.println("Vehicle deleted: " + vehicle.getPermitNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button filterButton;

    @FXML
    void onFilterClick(ActionEvent event) {
        try {
            List<Vehicle> filtered = DbHelper.getVehiclesWithHighCapacity(500);
            vehicleList.setAll(filtered); // vehicleList لازم يكون ObservableList<Vehicle>
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}