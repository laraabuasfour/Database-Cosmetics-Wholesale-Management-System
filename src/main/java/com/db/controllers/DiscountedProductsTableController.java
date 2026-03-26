package com.db.controllers;

import com.db.database.DbHelper;
import com.db.models.ProductDiscountView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class DiscountedProductsTableController {

    @FXML
    private Button closeBtn;

    @FXML
    private TableView<ProductDiscountView> discountTable;

    @FXML
    private TableColumn<ProductDiscountView, Integer> id; // Product Number (PN)

    @FXML
    private TableColumn<ProductDiscountView, String> name;

    @FXML
    private TableColumn<ProductDiscountView, Integer> quantity;

    @FXML
    private TableColumn<ProductDiscountView, BigDecimal> sellingPrice;

    @FXML
    private TableColumn<ProductDiscountView, BigDecimal> discountedPrice;

    @FXML
    private TableColumn<ProductDiscountView, LocalDate> expiringDate;

    private final ObservableList<ProductDiscountView> discountList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("PN"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("totalQuantity"));
        sellingPrice.setCellValueFactory(new PropertyValueFactory<>("originalPrice"));
        expiringDate.setCellValueFactory(new PropertyValueFactory<>("expiringDate"));
        discountedPrice.setCellValueFactory(new PropertyValueFactory<>("discountedPrice"));

        discountTable.setItems(discountList);
        loadDiscountedProducts();
    }

    private void loadDiscountedProducts() {
        List<ProductDiscountView> list = DbHelper.getExpiringProductsDiscounted();
        discountList.setAll(list);
    }

    @FXML
    void closedClicked(ActionEvent event) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

}
