package com.db.controllers;

import com.db.database.DbHelper;
import com.db.models.Warehouse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class WarehouseInfoSaveController {

    @FXML
    private Button closeBtn;

    @FXML
    private Label errorLabel;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField warehouse_cap;

    @FXML
    private TextField warehouse_locat;

    private Warehouse warehouseToUpdate;
    private boolean isUpdate = false;
    private Runnable callback;

    @FXML
    void closeClicked(ActionEvent event) {
        Stage stage = (Stage) saveBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save(ActionEvent event) {
        saveBtn.setDisable(true);
        closeBtn.setDisable(true);

        String locationValue = warehouse_locat.getText();
        String capacityValue = warehouse_cap.getText();

        if (!validateData(locationValue, capacityValue)) {
            saveBtn.setDisable(false);
            closeBtn.setDisable(false);
            return;
        }

        int capacity = Integer.parseInt(capacityValue);

        if (isUpdate) {
            warehouseToUpdate.setLocation(locationValue);
            warehouseToUpdate.setCapacity(capacity);
            DbHelper.updateWarehouse(warehouseToUpdate);
        } else {
            Warehouse warehouse = new Warehouse();
            warehouse.setWarehouseId(DbHelper.getNextWarehouseId());
            warehouse.setLocation(locationValue);
            warehouse.setCapacity(capacity);
            DbHelper.addWarehouse(warehouse);
        }

        if (callback != null) {
            callback.run();
        }

        Stage stage = (Stage) saveBtn.getScene().getWindow();
        stage.close();
    }

    public void setWarehouseToUpdate(Warehouse warehouseToUpdate) {
        this.warehouseToUpdate = warehouseToUpdate;
        if (warehouseToUpdate != null) {
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
        warehouse_locat.setText(warehouseToUpdate.getLocation());
        warehouse_cap.setText(String.valueOf(warehouseToUpdate.getCapacity()));
    }

    private boolean validateData(String locationValue, String capacityValue) {
        if (locationValue == null || locationValue.isEmpty()) {
            errorLabel.setText("Location is required");
            return false;
        } else if (capacityValue == null || capacityValue.isEmpty()) {
            errorLabel.setText("Capacity is required");
            return false;
        }

        try {
            int cap = Integer.parseInt(capacityValue);
            if (cap < 0) {
                errorLabel.setText("Capacity must be a positive number");
                return false;
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Capacity must be a valid number");
            return false;
        }

        return true;
    }
}
