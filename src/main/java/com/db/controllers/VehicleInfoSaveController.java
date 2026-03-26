package com.db.controllers;

import com.db.database.DbHelper;
import com.db.models.Vehicle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VehicleInfoSaveController {
    @FXML
    private TextField Vcapacity;

    @FXML
    private Button closeBtn;

    @FXML
    private TextField empID;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField phoneNumber;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField vehnum;

    @FXML
    private TextField permitNumber;

    private Vehicle vehicleToUpdate;
    private boolean isUpdate = false;
    private Runnable callback;

    @FXML
    void save(ActionEvent event) {
        saveBtn.setDisable(true);
        closeBtn.setDisable(true);

        String permit = permitNumber.getText();
        String capacityStr = Vcapacity.getText();
        String vnum = vehnum.getText();
        String phone = phoneNumber.getText();
        String eidStr = empID.getText();

        if (!validateData(permit, capacityStr, vnum, phone, eidStr)) {
            saveBtn.setDisable(false);
            closeBtn.setDisable(false);
            return;
        }

        int capacity = Integer.parseInt(capacityStr);
        int employeeId = Integer.parseInt(eidStr);

        if (!DbHelper.employeeExists(employeeId)) { //q3
            errorLabel.setText("Employee not found! Please enter a valid employee number.");
            saveBtn.setDisable(false);
            closeBtn.setDisable(false);
            return; 
        }

        if (isUpdate) {
            vehicleToUpdate.setPermitNumber(permit);
            vehicleToUpdate.setCapacity(capacity);
            vehicleToUpdate.setVehicleNumber(vnum);
            vehicleToUpdate.setPhoneNumber(phone);
            vehicleToUpdate.setEmployeeId(employeeId);
            DbHelper.updateVehicle(vehicleToUpdate);
        } else {
            Vehicle newVehicle = new Vehicle(permit, capacity, vnum, phone, employeeId);
            DbHelper.addVehicle(newVehicle);
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

    public void setVehicleToUpdate(Vehicle vehicleToUpdate) {
        this.vehicleToUpdate = vehicleToUpdate;
        if (vehicleToUpdate != null) {
            populateFields();
        }
    }

    public void setUpdate(boolean update) {
        this.isUpdate = update;
    }

    public void setCallbackFunction(Runnable callback) {
        this.callback = callback;
    }

    private void populateFields() {
        permitNumber.setText(vehicleToUpdate.getPermitNumber());
        Vcapacity.setText(String.valueOf(vehicleToUpdate.getCapacity()));
        vehnum.setText(vehicleToUpdate.getVehicleNumber());
        phoneNumber.setText(vehicleToUpdate.getPhoneNumber());
        empID.setText(String.valueOf(vehicleToUpdate.getEmployeeId()));
    }

    private boolean validateData(String permit, String capStr, String vnum, String phone, String eidStr) {
        if (permit == null || permit.trim().isEmpty()) {
            errorLabel.setText("Permit number is required");
            return false;
        } else if (capStr == null || !capStr.matches("\\d+")) {
            errorLabel.setText("Capacity must be a number");
            return false;
        } else if (vnum == null || vnum.trim().isEmpty()) {
            errorLabel.setText("Vehicle number is required");
            return false;
        } else if (phone == null || phone.trim().isEmpty()) {
            errorLabel.setText("Phone number is required");
            return false;
        } else if (eidStr == null || !eidStr.matches("\\d+")) {
            errorLabel.setText("Employee ID must be a number");
            return false;
        }
        return true;
    }

}