package com.db.controllers;

import com.db.database.DbHelper;
import com.db.models.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductInfoSaveController {

    @FXML
    private TextField buyingPrice;

    @FXML
    private Button closeButton;

    @FXML
    private DatePicker expiringDate;

    @FXML
    private TextField name;


    @FXML
    private TextField quantity;

    @FXML
    private Button saveButton;

    @FXML
    private TextField sellingPrice;

    @FXML
    private Label errorLabel;

    private Product productToUpdate;
    private boolean isUpdate = false;
    private Runnable callback;

    @FXML
    void closeClicked(ActionEvent event) {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save(ActionEvent event) {
        saveButton.setDisable(true);
        closeButton.setDisable(true);

        String nameValue = name.getText();
        String sellingPriceValue = sellingPrice.getText();
        String buyingPriceValue = buyingPrice.getText();
        String quantityValue = quantity.getText();
        LocalDate expiringDateValue = expiringDate.getValue();
        if (!validateData(nameValue, sellingPriceValue, buyingPriceValue, quantityValue, expiringDateValue)) {
            saveButton.setDisable(false);
            closeButton.setDisable(false);
            return;
        }

        try {
            BigDecimal selling = new BigDecimal(sellingPriceValue);
            BigDecimal buying = new BigDecimal(buyingPriceValue);
            int qty = Integer.parseInt(quantityValue);
            LocalDate expDate = expiringDateValue;
            if (isUpdate) {
                productToUpdate.setName(nameValue);
                productToUpdate.setSellingPrice(selling);
                productToUpdate.setBuyingPrice(buying);
                productToUpdate.setQuantity(qty);
                productToUpdate.setExpiringDate(expDate);
                DbHelper.updateProduct(productToUpdate);
            } else {
                Product product = new Product();
                product.setProductId(DbHelper.getNextProductId());
                product.setName(nameValue);
                product.setSellingPrice(selling);
                product.setBuyingPrice(buying);
                product.setQuantity(qty);
                product.setExpiringDate(expDate);
                DbHelper.addProduct(product);
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

    public void setProductToUpdate(Product productToUpdate) {
        this.productToUpdate = productToUpdate;
        if (productToUpdate != null) {
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
        name.setText(productToUpdate.getName());
        sellingPrice.setText(productToUpdate.getSellingPrice().toString());
        buyingPrice.setText(productToUpdate.getBuyingPrice().toString());
        quantity.setText(String.valueOf(productToUpdate.getQuantity()));
        expiringDate.setValue(productToUpdate.getExpiringDate());
    }

    private boolean validateData(String nameValue, String sellingPriceValue, String buyingPriceValue,
                                 String quantityValue, LocalDate expiringDateValue) {
        if (nameValue == null || nameValue.isEmpty()) {
            errorLabel.setText("Product name is required");
            return false;
        }
        if (sellingPriceValue == null || sellingPriceValue.isEmpty()) {
            errorLabel.setText("Selling price is required");
            return false;
        }
        if (buyingPriceValue == null || buyingPriceValue.isEmpty()) {
            errorLabel.setText("Buying price is required");
            return false;
        }
        if (quantityValue == null || quantityValue.isEmpty()) {
            errorLabel.setText("Quantity is required");
            return false;
        }
        if (expiringDateValue == null) {
            errorLabel.setText("Expiring date is required");
            return false;
        }
        return true;
    }
}
