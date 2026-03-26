package com.db.controllers;

import com.db.database.DbHelper;
import com.db.models.Bills;
import com.db.models.Orders;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class BillsInfoSaveController {

    // Inner class to represent order in ComboBox
    public static class OrderComboItem {
        private final int orderId;
        private final String storeName;
        private final LocalDate orderDate;

        public OrderComboItem(int orderId, String storeName, LocalDate orderDate) {
            this.orderId = orderId;
            this.storeName = storeName;
            this.orderDate = orderDate;
        }

        public int getOrderId() {
            return orderId;
        }

        public String getStoreName() {
            return storeName;
        }

        public LocalDate getOrderDate() {
            return orderDate;
        }

        @Override
        public String toString() {
            return orderId + " - " + storeName + " (" + orderDate + ")";
        }
    }

    @FXML
    private Button closeBtn;

    @FXML
    private DatePicker dateOfTransaction;

    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox<OrderComboItem> orderComboBox;

    @FXML
    private TextField price;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField type;

    private Bills billToUpdate;
    private boolean isUpdate = false;
    private Runnable callback;

    @FXML
    public void initialize() {
        loadOrders();
    }

    private void loadOrders() {
        try {
            List<Orders> orders = DbHelper.getAllOrders();
            for (Orders order : orders) {
                OrderComboItem item = new OrderComboItem(
                    order.getOrderId(),
                    order.getStoreName(),
                    order.getOrderDate()
                );
                orderComboBox.getItems().add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Error loading orders: " + e.getMessage());
        }
    }

    @FXML
    void closeClicked(ActionEvent event) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save(ActionEvent event) {
        saveBtn.setDisable(true);
        closeBtn.setDisable(true);

        LocalDate dateValue = dateOfTransaction.getValue();
        String typeValue = type.getText();
        String priceValue = price.getText();
        OrderComboItem selectedOrder = orderComboBox.getValue();

        if (!validateData(dateValue, typeValue, priceValue, selectedOrder)) {
            saveBtn.setDisable(false);
            closeBtn.setDisable(false);
            return;
        }

        BigDecimal parsedPrice = new BigDecimal(priceValue);
        int parsedOrderId = selectedOrder.getOrderId();

        if (isUpdate) {
            billToUpdate.setDateOfTransaction(dateValue);
            billToUpdate.setType(typeValue);
            billToUpdate.setTotalPrice(parsedPrice);
            billToUpdate.setOrderId(parsedOrderId);
            DbHelper.updateBill(billToUpdate);
        } else {
            Bills bill = new Bills();
            bill.setBillId(DbHelper.getNextBillId());
            bill.setDateOfTransaction(dateValue);
            bill.setType(typeValue);
            bill.setTotalPrice(parsedPrice);
            bill.setOrderId(parsedOrderId);
            DbHelper.addBill(bill);
        }

        if (callback != null) {
            callback.run();
        }

        Stage stage = (Stage) saveBtn.getScene().getWindow();
        stage.close();
    }

    public void setBillToUpdate(Bills billToUpdate) {
        this.billToUpdate = billToUpdate;
        if (billToUpdate != null) {
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
        dateOfTransaction.setValue(billToUpdate.getDateOfTransaction());
        type.setText(billToUpdate.getType());
        price.setText(billToUpdate.getTotalPrice().toString());
        
        // Find and select the correct order in the ComboBox
        int orderIdToSelect = billToUpdate.getOrderId();
        for (OrderComboItem item : orderComboBox.getItems()) {
            if (item.getOrderId() == orderIdToSelect) {
                orderComboBox.setValue(item);
                break;
            }
        }
    }

    private boolean validateData(LocalDate date, String type, String price, OrderComboItem selectedOrder) {
        try {
            if (date == null) {
                errorLabel.setText("Date is required");
                return false;
            }

            if (type == null || type.isEmpty()) {
                errorLabel.setText("Type is required");
                return false;
            }

            if (price == null || price.isEmpty()) {
                errorLabel.setText("Price is required");
                return false;
            }
            new BigDecimal(price); // throws if invalid

            if (selectedOrder == null) {
                errorLabel.setText("Order selection is required");
                return false;
            }

        } catch (DateTimeParseException e) {
            errorLabel.setText("Invalid date format (use yyyy-MM-dd)");
            return false;
        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid number format for price");
            return false;
        }

        return true;
    }
}
