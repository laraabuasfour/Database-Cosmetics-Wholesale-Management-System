package com.db.controllers;

import com.db.database.DbHelper;
import com.db.models.Orders;
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

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class OrdersTableController {

    @FXML
    private TableColumn<Orders, Void> actions;
    @FXML
    private TableColumn<Orders, Void> viewOrderDetails;

    @FXML
    private Button btnId;

    @FXML
    private TableColumn<Orders, String> empName;

    @FXML
    private TableColumn<Orders, Integer> id;

    @FXML
    private TableColumn<Orders, LocalDate> orderDate;

    @FXML
    private TableColumn<Orders, String> storeName;


    @FXML
    private TableView<Orders> orderTable;
    
    // Filter controls
    @FXML
    private TextField nameSearchField;
    
    @FXML
    private ComboBox<String> orderByColumnCombo;
    
    @FXML
    private ComboBox<String> orderDirectionCombo;
    
    @FXML
    private Button applyFiltersButton;
    
    @FXML
    private Button clearFiltersButton;

    private ObservableList<Orders> orderList = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws Exception {
        id.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        storeName.setCellValueFactory(new PropertyValueFactory<>("storeName"));
        empName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        orderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        actions.setCellFactory(col -> new TableCell<Orders, Void>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox box = new HBox(5, editButton, deleteButton);

            {
                box.setAlignment(Pos.CENTER);

                editButton.setOnAction(e -> {
                    Orders order = getTableView().getItems().get(getIndex());
                    openCreateUpdateScene(true, order);
                });

                deleteButton.setOnAction(e -> {
                    Orders order = getTableView().getItems().get(getIndex());
                    showDeleteConfirmationDialog(order);
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

        viewOrderDetails.setCellFactory(col -> new TableCell<Orders, Void>() {
            private final Button viewButton = new Button("View");
            private final HBox box = new HBox(5, viewButton);

            {
                box.setAlignment(Pos.CENTER);

                viewButton.setOnAction(e -> {
                    Orders order = getTableView().getItems().get(getIndex());
                    openOrderDetailsScene(order);
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

        orderTable.setItems(orderList);
        
        // Initialize filter dropdowns
        initializeFilterControls();
        
        refreshTable();
    }

    @FXML
    void onClick(ActionEvent event) throws Exception {
        openCreateUpdateScene(false, null);
    }
    
    @FXML
    void onApplyFilters(ActionEvent event) throws Exception {
        applyFilters();
    }
    
    @FXML
    void onClearFilters(ActionEvent event) throws Exception {
        clearAllFilters();
        refreshTable();
    }
    
    private void initializeFilterControls() {
        // Populate order by column dropdown
        orderByColumnCombo.getItems().addAll(
            "Order ID", "Store Name", "Employee Name", "Order Date"
        );
        orderByColumnCombo.setValue("Order ID"); // Default selection
        
        // Populate order direction dropdown
        orderDirectionCombo.getItems().addAll("ASC", "DESC");
        orderDirectionCombo.setValue("ASC"); // Default selection
    }
    
    private void applyFilters() throws Exception {
        String nameSearch = nameSearchField.getText().trim().isEmpty() ? null : nameSearchField.getText().trim();
        String orderByColumn = orderByColumnCombo.getValue();
        String orderDirection = orderDirectionCombo.getValue();
        
        // Map display names to database column names
        String dbColumnName = mapDisplayNameToDbColumn(orderByColumn);
        
        orderList.clear();
        List<Orders> filteredOrders = DbHelper.filterAndOrderOrders(nameSearch, dbColumnName, orderDirection);
        orderList.setAll(filteredOrders);
    }
    
    private void clearAllFilters() {
        nameSearchField.clear();
        orderByColumnCombo.setValue("Order ID");
        orderDirectionCombo.setValue("ASC");
    }
    
    private String mapDisplayNameToDbColumn(String displayName) {
        switch (displayName) {
            case "Order ID": return "order_id";
            case "Store Name": return "store_name";
            case "Employee Name": return "employee_name";
            case "Order Date": return "order_date";
            default: return "order_id";
        }
    }

    private void refreshTable() throws Exception {
        orderList.clear();
        List<Orders> orders = DbHelper.getAllOrders(); // Make sure this method exists
        orderList.setAll(orders);
    }

    private void openCreateUpdateScene(boolean isUpdate, Orders order) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/OrdersCreateUpdate.fxml"));
            Parent root = loader.load();

            OrdersInfoSaveController controller = loader.getController(); // Make sure this controller exists
            controller.setUpdate(isUpdate);
            if (isUpdate) {
                controller.setOrderToUpdate(order);
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
            stage.setTitle(isUpdate ? "Edit Order" : "Add New Order");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showDeleteConfirmationDialog(Orders order) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Order");
        alert.setHeaderText("Are you sure you want to delete this order?");
        alert.setContentText("Order ID: " + order.getOrderId() + " will be deleted.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                deleteOrder(order);
            }
        });
    }

    private void deleteOrder(Orders order) {
        try {
            DbHelper.deleteOrder(order.getOrderId()); // Implement this method
            refreshTable();
            System.out.println("Order deleted: " + order.getOrderId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void openOrderDetailsScene(Orders order) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/OrderDetailsTable.fxml"));
            Parent root = loader.load();

            // Get the controller and pass the orderId
            OrderDetailsTableController controller = loader.getController();
            controller.setOrderId(order.getOrderId());

            Stage stage = new Stage();
            stage.setTitle("Order Details - Order #" + order.getOrderId());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
