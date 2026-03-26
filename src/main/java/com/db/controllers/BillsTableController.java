package com.db.controllers;

import java.io.IOException;
import java.util.List;

import com.db.database.DbHelper;
import com.db.models.Bills;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class BillsTableController {

    @FXML
    private TableColumn<Bills, Void> actions;

    @FXML
    private TableView<Bills> billsTable;

    @FXML
    private Button btnId;

    @FXML
    private TableColumn<Bills, Integer> id;

    @FXML
    private TableColumn<Bills, String> dateOfTransaction;

    @FXML
    private TableColumn<Bills, String> type;

    @FXML
    private TableColumn<Bills, Double> price;

    @FXML
    private TableColumn<Bills, Integer> orderId;
    
    // Statistics labels
    @FXML
    private Label maxBillLabel;
    
    @FXML
    private Label minBillLabel;
    
    @FXML
    private Label billCountLabel;

    private ObservableList<Bills> billList = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws Exception {
        id.setCellValueFactory(new PropertyValueFactory<>("billId"));
        dateOfTransaction.setCellValueFactory(new PropertyValueFactory<>("dateOfTransaction"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        price.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        orderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        actions.setCellFactory(col -> new TableCell<Bills, Void>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox box = new HBox(5, editButton, deleteButton);

            {
                box.setAlignment(Pos.CENTER);

                editButton.setOnAction(e -> {
                    Bills bill = getTableView().getItems().get(getIndex());
                    openCreateUpdateScene(true, bill);
                });

                deleteButton.setOnAction(e -> {
                    Bills bill = getTableView().getItems().get(getIndex());
                    showDeleteConfirmationDialog(bill);
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

        billsTable.setItems(billList);
        refreshTable();
        updateStatistics();
    }

    @FXML
    void onClick(ActionEvent event) throws Exception {
        openCreateUpdateScene(false, null);
    }

    private void refreshTable() throws Exception {
        billList.clear();
        List<Bills> bills = DbHelper.getAllBills();
        billList.setAll(bills);
        updateStatistics();
    }
    
    private void updateStatistics() {
        try {
            DbHelper.BillStatistics stats = DbHelper.getBillStatistics();
            maxBillLabel.setText(String.format("$%.2f", stats.getMaxBillValue()));
            minBillLabel.setText(String.format("$%.2f", stats.getMinBillValue()));
            billCountLabel.setText(String.valueOf(stats.getBillCount()));
        } catch (Exception e) {
            e.printStackTrace();
            maxBillLabel.setText("$0.00");
            minBillLabel.setText("$0.00");
            billCountLabel.setText("0");
        }
    }

    private void openCreateUpdateScene(boolean isUpdate, Bills bill) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BillsCreateUpdate.fxml"));
            Parent root = loader.load();

            BillsInfoSaveController controller = loader.getController();
            controller.setUpdate(isUpdate);
            if (isUpdate) {
                controller.setBillToUpdate(bill);
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
            stage.setTitle("Create or Update Bill");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showDeleteConfirmationDialog(Bills bill) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Bill");
        alert.setHeaderText("Are you sure you want to delete this bill?");
        alert.setContentText("Bill ID: " + bill.getBillId() + " will be deleted.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                deleteBill(bill);
            } else {
                System.out.println("Delete canceled");
            }
        });
    }

    private void deleteBill(Bills bill) {
        try {
            DbHelper.deleteBill(bill.getBillId());
            refreshTable();
            System.out.println("Bill deleted: " + bill.getBillId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
