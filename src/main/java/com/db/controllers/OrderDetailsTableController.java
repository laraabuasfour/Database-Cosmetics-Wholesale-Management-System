package com.db.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.db.database.DbHelper;
import com.db.models.OrderDetailsView;
import com.db.models.Orders;

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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OrderDetailsTableController {

    @FXML
    private Button btnId;

    @FXML
    private Button addProductBtn;

    @FXML
    private TableColumn<OrderDetailsView, String> productName;

    @FXML
    private TableColumn<OrderDetailsView, Integer> productNum;

    @FXML
    private TableColumn<OrderDetailsView, Integer> quantity;

    @FXML
    private TableColumn<OrderDetailsView, BigDecimal> sellingPrice;

    @FXML
    private TableColumn<OrderDetailsView, Void> actions;

    @FXML
    private TableView<OrderDetailsView> orderDetailsTable;
    
    // Order information labels
    @FXML
    private Label orderIdLabel;
    
    @FXML
    private Label employeeNameLabel;
    
    @FXML
    private Label storeNameLabel;
    
    @FXML
    private Label orderDateLabel;
    
    @FXML
    private Label totalSumLabel;

    private final ObservableList<OrderDetailsView> viewList = FXCollections.observableArrayList();

    private int orderId; // to be set from outside

    @FXML
    public void initialize() {
        productNum.setCellValueFactory(new PropertyValueFactory<>("productNum"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        sellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        actions.setCellFactory(col -> new TableCell<OrderDetailsView, Void>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox box = new HBox(5, editButton, deleteButton);

            {
                box.setAlignment(Pos.CENTER);
                editButton.setStyle("-fx-font-size: 10px; -fx-padding: 2 6 2 6;");
                deleteButton.setStyle("-fx-font-size: 10px; -fx-padding: 2 6 2 6;");

                editButton.setOnAction(e -> {
                    OrderDetailsView orderDetail = getTableView().getItems().get(getIndex());
                    openEditOrderDetailDialog(orderDetail);
                });

                deleteButton.setOnAction(e -> {
                    OrderDetailsView orderDetail = getTableView().getItems().get(getIndex());
                    showDeleteConfirmationDialog(orderDetail);
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

        orderDetailsTable.setItems(viewList);
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
        loadOrderInformation();
        refreshTable();
    }

    private void refreshTable() {
        viewList.clear();
        List<OrderDetailsView> rows = DbHelper.getOrderDetailsByOrderId(orderId); // updated method
        viewList.setAll(rows);
        calculateTotal();
    }
    
    private void loadOrderInformation() {
        try {
            Orders order = DbHelper.getOrderById(orderId);
            if (order != null) {
                orderIdLabel.setText(String.valueOf(order.getOrderId()));
                employeeNameLabel.setText(order.getEmployeeName() != null ? order.getEmployeeName() : "N/A");
                storeNameLabel.setText(order.getStoreName() != null ? order.getStoreName() : "N/A");
                orderDateLabel.setText(order.getOrderDate() != null ? order.getOrderDate().toString() : "N/A");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Set default values if there's an error
            orderIdLabel.setText("N/A");
            employeeNameLabel.setText("N/A");
            storeNameLabel.setText("N/A");
            orderDateLabel.setText("N/A");
        }
    }
    
    private void calculateTotal() {
        try {
            BigDecimal total = DbHelper.calculateOrderTotal(orderId);
            totalSumLabel.setText(String.format("$%.2f", total));
        } catch (Exception e) {
            e.printStackTrace();
            totalSumLabel.setText("$0.00");
        }
    }

    @FXML
    void onClick(ActionEvent event) {
        Stage stage = (Stage) btnId.getScene().getWindow();
        stage.close();
    }

    @FXML
    void addProductClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddProductToOrder.fxml"));
            Parent root = loader.load();

            AddProductToOrderController controller = loader.getController();
            controller.setOrderId(orderId);
            controller.setCallbackFunction(this::refreshTable);

            Stage stage = new Stage();
            stage.setTitle("Add Product to Order");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showDeleteConfirmationDialog(OrderDetailsView orderDetail) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Order Detail");
        alert.setHeaderText("Are you sure you want to delete this order detail?");
        alert.setContentText("Product: " + orderDetail.getProductName() + " will be removed from this order.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                deleteOrderDetail(orderDetail);
            }
        });
    }

    private void deleteOrderDetail(OrderDetailsView orderDetail) {
        try {
            boolean success = DbHelper.deleteOrderDetail(orderId, orderDetail.getProductNum());
            if (success) {
                refreshTable();
                System.out.println("Order detail deleted: " + orderDetail.getProductName());
            } else {
                System.out.println("Failed to delete order detail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openEditOrderDetailDialog(OrderDetailsView orderDetail) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddProductToOrder.fxml"));
            Parent root = loader.load();

            AddProductToOrderController controller = loader.getController();
            controller.setOrderId(orderId);
            controller.setUpdate(true);
            controller.setOrderDetailToUpdate(orderDetail);
            controller.setCallbackFunction(this::refreshTable);

            Stage stage = new Stage();
            stage.setTitle("Edit Order Detail");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
