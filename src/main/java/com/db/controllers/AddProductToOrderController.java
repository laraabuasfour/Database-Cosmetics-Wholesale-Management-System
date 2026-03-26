package com.db.controllers;

import java.util.List;

import com.db.database.DbHelper;
import com.db.models.OrderDetailsView;
import com.db.models.Product;
import com.db.models.ProductComboItem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddProductToOrderController {

    @FXML
    private Label titleLabel;

    @FXML
    private ComboBox<ProductComboItem> productComboBox;

    @FXML
    private Label sellingPriceLabel;

    @FXML
    private TextField quantityField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button addBtn;

    @FXML
    private Button cancelBtn;

    private int orderId;
    private Runnable callback;
    private boolean isUpdate = false;
    private OrderDetailsView orderDetailToUpdate;

    @FXML
    public void initialize() {
        loadProducts();
        setupProductSelectionListener();
    }

    private void loadProducts() {
        try {
            List<Product> products = DbHelper.getAllProducts();
            for (Product product : products) {
                ProductComboItem item = new ProductComboItem(
                    product.getProductId(),
                    product.getName(),
                    product.getSellingPrice()
                );
                productComboBox.getItems().add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Error loading products: " + e.getMessage());
        }
    }

    private void setupProductSelectionListener() {
        productComboBox.setOnAction(event -> {
            ProductComboItem selectedProduct = productComboBox.getValue();
            if (selectedProduct != null) {
                sellingPriceLabel.setText("$" + selectedProduct.getSellingPrice().toString());
            } else {
                sellingPriceLabel.setText("Select a product");
            }
        });
    }

    @FXML
    void addProduct(ActionEvent event) {
        addBtn.setDisable(true);
        cancelBtn.setDisable(true);

        ProductComboItem selectedProduct = productComboBox.getValue();
        String quantityText = quantityField.getText();

        if (!validateData(selectedProduct, quantityText)) {
            addBtn.setDisable(false);
            cancelBtn.setDisable(false);
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityText);
            int productId = selectedProduct.getProductId();

            boolean success;
            if (isUpdate) {
                // Update existing order detail
                success = DbHelper.updateOrderDetail(orderId, orderDetailToUpdate.getProductNum(), quantity);
            } else {
                // Add new product to order details
                success = DbHelper.addOrderDetail(orderId, productId, quantity);
            }
            
            if (success) {
                if (callback != null) {
                    callback.run();
                }
                Stage stage = (Stage) addBtn.getScene().getWindow();
                stage.close();
            } else {
                errorLabel.setText(isUpdate ? "Failed to update order detail" : "Failed to add product to order");
                addBtn.setDisable(false);
                cancelBtn.setDisable(false);
            }

        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid quantity format");
            addBtn.setDisable(false);
            cancelBtn.setDisable(false);
        } catch (Exception e) {
            errorLabel.setText("Error " + (isUpdate ? "updating" : "adding") + " product: " + e.getMessage());
            addBtn.setDisable(false);
            cancelBtn.setDisable(false);
        }
    }

    @FXML
    void cancelClicked(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setCallbackFunction(Runnable runnable) {
        this.callback = runnable;
    }

    public void setUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
        updateUI();
    }

    private void updateUI() {
        if (isUpdate) {
            titleLabel.setText("Edit Order Detail");
            addBtn.setText("Update");
        } else {
            titleLabel.setText("Add Product to Order");
            addBtn.setText("Add");
        }
    }

    public void setOrderDetailToUpdate(OrderDetailsView orderDetail) {
        this.orderDetailToUpdate = orderDetail;
        if (orderDetail != null) {
            populateFields();
        }
    }

    private void populateFields() {
        if (orderDetailToUpdate != null) {
            // Find and select the product in the ComboBox
            for (ProductComboItem item : productComboBox.getItems()) {
                if (item.getProductId() == orderDetailToUpdate.getProductNum()) {
                    productComboBox.setValue(item);
                    sellingPriceLabel.setText("$" + item.getSellingPrice().toString());
                    break;
                }
            }
            
            // Set quantity
            quantityField.setText(String.valueOf(orderDetailToUpdate.getQuantity()));
            
            // In edit mode, disable the product selection
            if (isUpdate) {
                productComboBox.setDisable(true);
            }
        }
    }

    private boolean validateData(ProductComboItem selectedProduct, String quantityText) {
        if (selectedProduct == null) {
            errorLabel.setText("Please select a product");
            return false;
        }

        if (quantityText == null || quantityText.trim().isEmpty()) {
            errorLabel.setText("Quantity is required");
            return false;
        }

        try {
            int quantity = Integer.parseInt(quantityText.trim());
            if (quantity <= 0) {
                errorLabel.setText("Quantity must be greater than 0");
                return false;
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid quantity format");
            return false;
        }

        return true;
    }
}