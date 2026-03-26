package com.db.controllers;

import com.db.database.DbHelper;
import com.db.models.Supplier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SupplierInfoSaveController {

    @FXML
    private TextField supplierLocation;

    @FXML
    private TextField name;

    @FXML
    private TextField phoneNumber;

    @FXML
    private Button saveBtn;

    @FXML
    private Label errorLabel;

    @FXML
    private Button closeBtn;

    private Supplier supplierToUpdate;
    private boolean isUpdate = false;
    private Runnable callback;

    @FXML
    void save(ActionEvent event) {
        saveBtn.setDisable(true);
        closeBtn.setDisable(true);
        String nameValue = name.getText();
        String phoneNumberValue = phoneNumber.getText();
        String locationValue = supplierLocation.getText();
        if (!validateData(nameValue, phoneNumberValue, locationValue)) {
            saveBtn.setDisable(false);
            closeBtn.setDisable(false);
            return;
        }

        if (isUpdate) {
            supplierToUpdate.setName(nameValue);
            supplierToUpdate.setPhoneNumber(phoneNumberValue);
            supplierToUpdate.setLocation(locationValue);
            DbHelper.updateSupplier(supplierToUpdate);
        } else {
            Supplier supplier = new Supplier();
            supplier.setSupplierId(DbHelper.getNextSupplierId());
            supplier.setName(nameValue);
            supplier.setPhoneNumber(phoneNumberValue);
            supplier.setLocation(locationValue);
            DbHelper.addSupplier(supplier);
        }

        if (callback != null) {
            callback.run();
        }
        Stage stage = (Stage) saveBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void closeClicked(ActionEvent event) {
        Stage stage = (Stage) saveBtn.getScene().getWindow();
        stage.close();
    }

    public void setSupplierToUpdate(Supplier supplierToUpdate) {
        this.supplierToUpdate = supplierToUpdate;
        if (supplierToUpdate != null) {
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
        name.setText(supplierToUpdate.getName());
        phoneNumber.setText(supplierToUpdate.getPhoneNumber());
        supplierLocation.setText(supplierToUpdate.getLocation());
    }

    private boolean validateData(String nameValue, String phoneNumberValue, String locationValue) {
        if (nameValue == null || nameValue.isEmpty()) {
            errorLabel.setText("Name is required");
            return false;
        } else if (phoneNumberValue == null || phoneNumberValue.isEmpty()) {
            errorLabel.setText("Phone Number is required");
            return false;

        } else if (locationValue == null || locationValue.isEmpty()) {
            errorLabel.setText("Location is required");
            return false;
        }
        return true;

    }
}
