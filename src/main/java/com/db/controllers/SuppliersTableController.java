
package com.db.controllers;

import com.db.database.DbHelper;
import com.db.models.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SuppliersTableController {
    @FXML
    private Button btnId;

    @FXML
    private TableColumn<Supplier, Integer> id;

    @FXML
    private TableColumn<Supplier, String> location1;

    @FXML
    private TableColumn<Supplier, String> name;

    @FXML
    private TableColumn<Supplier, String> phoneNumber;

    @FXML
    private TableColumn<Supplier, Void> actions;

    @FXML
    private TableView<Supplier> supplierTable;

    @FXML //Q1
    private Button btnChart;
    @FXML //Q1
    private Label statsLabel;

    @FXML //q1
    private TextField searchField;

    @FXML //q1
    void onSearch(KeyEvent event) {
        String keyword = searchField.getText().toLowerCase();
        List<Supplier> filtered = supplierList.stream()
                .filter(s -> s.getName().toLowerCase().contains(keyword) ||
                        s.getLocation().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        supplierTable.setItems(FXCollections.observableArrayList(filtered));
    }




        private ObservableList<Supplier> supplierList = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws Exception {
        id.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        location1.setCellValueFactory(new PropertyValueFactory<>("location"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        actions.setCellFactory(col -> new TableCell<Supplier, Void>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox box = new HBox(5, editButton, deleteButton);

            {
                box.setAlignment(Pos.CENTER);

                editButton.setOnAction(e -> {
                    Supplier supplier = getTableView().getItems().get(getIndex());
                    openCreateUpdateScene(true, supplier);
                });

                deleteButton.setOnAction(e -> {
                    Supplier supplier = getTableView().getItems().get(getIndex());
                    showDeleteConfirmationDialog(supplier);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null); // only show buttons for valid rows
                } else {
                    setGraphic(box);  // show buttons only on actual rows
                }
            }
        });



        supplierTable.setItems(supplierList);

        refreshTable();
        int total = supplierList.size(); //q1
        statsLabel.setText("Total Suppliers: " + total);//q1
    }

    @FXML
    void onClick(ActionEvent event) throws Exception {
        openCreateUpdateScene(false, null);
    }

    private void refreshTable() throws Exception {
        supplierList.clear();
        List<Supplier> suppliers = DbHelper.getAllSuppliers();
        supplierList.setAll(suppliers);
    }

    private void openCreateUpdateScene(boolean isUpdate, Supplier supplier) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SupplierCreateUpdate.fxml"));
            Parent root = loader.load();

            // Get the controller and pass the data
            SupplierInfoSaveController controller = loader.getController();
            controller.setUpdate(isUpdate);
            if (isUpdate) {
                controller.setSupplierToUpdate(supplier);
            }
            controller.setCallbackFunction(() -> {
                try {
                    refreshTable();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            // Open in a new window
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Create new supplier");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to show the confirmation dialog
    private void showDeleteConfirmationDialog(Supplier supplier) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Supplier");
        alert.setHeaderText("Are you sure you want to delete this supplier?");
        alert.setContentText("Supplier: " + supplier.getName() + " will be deleted.");

        // Wait for the user to click a button
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // If confirmed, delete the supplier
                deleteSupplier(supplier);
            } else {
                // If canceled, do nothing
                System.out.println("Delete canceled");
            }
        });
    }

    // Method to delete the supplier (from the DB)
    private void deleteSupplier(Supplier supplier) {
        try {
            // Your logic to delete from the database
            DbHelper.deleteSupplier(supplier.getSupplierId());

            // Refresh table after deletion
            refreshTable();
            System.out.println("Supplier deleted: " + supplier.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Q1
    public void openChart(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SupplierChart.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Suppliers by Location");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Q1
    @FXML
    void showChart(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SupplierChart.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Suppliers by Location");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //q1
    @FXML
    private void onSearch() {
        String keyword = searchField.getText().toLowerCase();

        ObservableList<Supplier> filteredList = FXCollections.observableArrayList();

        for (Supplier s : supplierList) {
            if (s.getName().toLowerCase().contains(keyword) || s.getLocation().toLowerCase().contains(keyword)) {
                filteredList.add(s);
            }
        }

        supplierTable.setItems(filteredList);
    }


}
