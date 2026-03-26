package com.db.controllers;

import com.db.database.DbHelper;
import com.db.models.Store;
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
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public class StoreTableController {
    @FXML
    private TableColumn<Store, Void> actions;

    @FXML
    private Button addButton;

    @FXML
    private TableColumn<Store, String> locationColumn;

    @FXML
    private TableColumn<Store, String> stname;     // storeName

    @FXML
    private TableColumn<Store, String> stnum;      // phoneNumber

    @FXML
    private TableView<Store> storeTable;

    private ObservableList<Store> storeList = FXCollections.observableArrayList();

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    void onSearchClick(ActionEvent event) {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            try {
                refreshTable();  // عرض كل البيانات إذا الحقل فاضي
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        try {
            List<Store> results = DbHelper.searchStoresByPhone(keyword);
            storeList.setAll(results);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() throws Exception {

        stname.setCellValueFactory(new PropertyValueFactory<>("storeName"));
        stnum.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        actions.setCellFactory(col -> new TableCell<Store, Void>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox box = new HBox(5, editButton, deleteButton);

            {
                box.setAlignment(Pos.CENTER);

                editButton.setOnAction(e -> {
                    Store store = getTableView().getItems().get(getIndex());
                    openCreateUpdateScene(true, store);
                });

                deleteButton.setOnAction(e -> {
                    Store store = getTableView().getItems().get(getIndex());
                    showDeleteConfirmationDialog(store);
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

        storeTable.setItems(storeList);
        refreshTable();
    }

    @FXML
    void onAddClick(ActionEvent event) throws Exception {
        openCreateUpdateScene(false, null);
    }

    private void refreshTable() throws Exception {
        storeList.clear();
        List<Store> stores = DbHelper.getAllStores();
        storeList.setAll(stores);
    }

    private void openCreateUpdateScene(boolean isUpdate, Store store) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StoreCreateUpdate.fxml"));
            Parent root = loader.load();

            StoreInfoSaveController controller = loader.getController();
            controller.setUpdate(isUpdate);
            if (isUpdate) {
                controller.setStoreToUpdate(store);
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
            stage.setTitle(isUpdate ? "Edit Store" : "Add New Store");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showDeleteConfirmationDialog(Store store) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Store");
        alert.setHeaderText("Are you sure you want to delete this store?");
        alert.setContentText("Store: " + store.getStoreName() + " will be deleted.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                deleteStore(store);
            }
        });
    }

    private void deleteStore(Store store) {
        try {
            DbHelper.deleteStore(store.getStoreName());
            refreshTable();
            System.out.println("Store deleted: " + store.getStoreName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private Button statsBtn;

    @FXML
    void openStatsView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StoreStatsView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Store Statistics");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
