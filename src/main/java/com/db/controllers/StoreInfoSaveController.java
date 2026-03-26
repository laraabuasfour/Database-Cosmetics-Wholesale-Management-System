package com.db.controllers;

import com.db.database.DbHelper;
import com.db.models.Store;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StoreInfoSaveController {
    @FXML
    private Button closebut;

    @FXML
    private Label errorLablel;

    @FXML
    private TextField locationField;

    @FXML
    private TextField name;

    @FXML
    private TextField phonenum;

    @FXML
    private Button savebut;

    private Store storeToUpdate;
    private boolean isUpdate = false;
    private Runnable callback;

    @FXML
    void save(ActionEvent event) {
        savebut.setDisable(true);
        closebut.setDisable(true);

        String nameValue = name.getText();
        String phoneValue = phonenum.getText();
        String locationValue = locationField.getText();

        if (!validateData(nameValue, phoneValue, locationValue)) {
            savebut.setDisable(false);
            closebut.setDisable(false);
            return;
        }

        if (isUpdate) {
            storeToUpdate.setStoreName(nameValue);
            storeToUpdate.setPhoneNumber(phoneValue);
            storeToUpdate.setLocation(locationValue);
            DbHelper.updateStore(storeToUpdate);
        } else {
            Store newStore = new Store(nameValue, phoneValue, locationValue);
            DbHelper.addStore(newStore);
        }

        if (callback != null) {
            callback.run();
        }

        Stage stage = (Stage) savebut.getScene().getWindow();
        stage.close();
    }

    @FXML
    void closeClicked(ActionEvent event) {
        Stage stage = (Stage) closebut.getScene().getWindow();
        stage.close();
    }

    public void setStoreToUpdate(Store storeToUpdate) {
        this.storeToUpdate = storeToUpdate;
        if (storeToUpdate != null) {
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
        name.setText(storeToUpdate.getStoreName());
        name.setDisable(true);
        phonenum.setText(storeToUpdate.getPhoneNumber());
        locationField.setText(storeToUpdate.getLocation());
    }

    private boolean validateData(String nameValue, String phoneValue, String locationValue) {
        if (nameValue == null || nameValue.trim().isEmpty()) {
            errorLablel.setText("Store name is required");
            return false;
        } else if (phoneValue == null || phoneValue.trim().isEmpty()) {
            errorLablel.setText("Phone number is required");
            return false;

        } else if (locationValue == null || locationValue.trim().isEmpty()) {
            errorLablel.setText("Location is required");
            return false;
        }
        return true;
    }

}