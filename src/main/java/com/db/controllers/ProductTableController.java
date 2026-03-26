package com.db.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.db.database.DbHelper;
import com.db.models.Product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ProductTableController {

    @FXML
    private TableColumn<Product, Void> actions;

    @FXML
    private Button addbtn;

    @FXML
    private Button discountBtn;

    // Filter controls
    @FXML
    private TextField nameField;

    @FXML
    private TextField minSellingPriceField;

    @FXML
    private TextField maxSellingPriceField;


    @FXML
    private DatePicker fromExpiringDatePicker;

    @FXML
    private DatePicker toExpiringDatePicker;

    @FXML
    private Button filterButton;

    @FXML
    private Button clearFiltersButton;

    // Chart controls
    @FXML
    private ComboBox<String> chartDataComboBox;

    @FXML
    private BarChart<String, Number> productChart;

    @FXML
    private CategoryAxis productAxis;

    @FXML
    private NumberAxis valueAxis;

    @FXML
    private TableColumn<Product, BigDecimal> buyingPrice;

    @FXML
    private TableColumn<Product, LocalDate> expiringDate;

    @FXML
    private TableColumn<Product, Integer> id;

    @FXML
    private TableColumn<Product, String> name;


    @FXML
    private TableColumn<Product, Integer> quantity;

    @FXML
    private TableColumn<Product, BigDecimal> sellingPrice;

    @FXML
    private TableView<Product> productTable;

    private ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws Exception {
        id.setCellValueFactory(new PropertyValueFactory<>("productId"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        sellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        buyingPrice.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        expiringDate.setCellValueFactory(new PropertyValueFactory<>("expiringDate"));

        actions.setCellFactory(col -> new TableCell<Product, Void>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox box = new HBox(5, editButton, deleteButton);

            {
                box.setAlignment(Pos.CENTER);

                editButton.setOnAction(e -> {
                    Product product = getTableView().getItems().get(getIndex());
                    openCreateUpdateScene(true, product);
                });

                deleteButton.setOnAction(e -> {
                    Product product = getTableView().getItems().get(getIndex());
                    showDeleteConfirmationDialog(product);
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

        productTable.setItems(productList);

        // Initialize chart dropdown
        initializeChartDropdown();

        refreshTable();
        loadChartData();
    }

    @FXML
    void addProduct(ActionEvent event) throws Exception {
        openCreateUpdateScene(false, null);
    }

    @FXML
    void discountedClick(ActionEvent event) throws Exception {
        openDiscountedProductsScene();
    }

    @FXML
    void onFilter(ActionEvent event) throws Exception {
        applyFilters();
    }

    @FXML
    void onClearFilters(ActionEvent event) throws Exception {
        clearAllFilters();
        refreshTable();
    }

    @FXML
    void onChartDataChange(ActionEvent event) {
        loadChartData();
    }

    private void refreshTable() throws Exception {
        productList.clear();
        List<Product> products = DbHelper.getAllProducts();
        productList.setAll(products);
    }

    private void applyFilters() throws Exception {
        // Get filter values
        String name = nameField.getText().trim().isEmpty() ? null : nameField.getText().trim();
        
        BigDecimal minSellingPrice = parseDecimal(minSellingPriceField.getText());
        BigDecimal maxSellingPrice = parseDecimal(maxSellingPriceField.getText());
        
        Date fromExpiringDate = fromExpiringDatePicker.getValue() != null ? 
            Date.valueOf(fromExpiringDatePicker.getValue()) : null;
        Date toExpiringDate = toExpiringDatePicker.getValue() != null ? 
            Date.valueOf(toExpiringDatePicker.getValue()) : null;

        // Apply filters via database query
        productList.clear();
        List<Product> filteredProducts = DbHelper.filterProducts(
            name, minSellingPrice, maxSellingPrice,
            fromExpiringDate, toExpiringDate
        );
        productList.setAll(filteredProducts);
    }

    private void clearAllFilters() {
        nameField.clear();
        minSellingPriceField.clear();
        maxSellingPriceField.clear();
        fromExpiringDatePicker.setValue(null);
        toExpiringDatePicker.setValue(null);
    }

    private BigDecimal parseDecimal(String text) {
        try {
            return text == null || text.trim().isEmpty() ? null : new BigDecimal(text.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer parseInteger(String text) {
        try {
            return text == null || text.trim().isEmpty() ? null : Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void openCreateUpdateScene(boolean isUpdate, Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProductCreateUpdate.fxml"));
            Parent root = loader.load();

            ProductInfoSaveController controller = loader.getController();
            controller.setUpdate(isUpdate);
            if (isUpdate) {
                controller.setProductToUpdate(product);
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
            stage.setTitle(isUpdate ? "Update Product" : "Create New Product");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showDeleteConfirmationDialog(Product product) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Product");
        alert.setHeaderText("Are you sure you want to delete this product?");
        alert.setContentText("Product: " + product.getName() + " will be deleted.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                deleteProduct(product);
            } else {
                System.out.println("Delete canceled");
            }
        });
    }

    private void deleteProduct(Product product) {
        try {
            DbHelper.deleteProduct(product.getProductId());
            refreshTable();
            System.out.println("Product deleted: " + product.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openDiscountedProductsScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DiscountedProductsTable.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Discounted Expiring Products");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeChartDropdown() {
        chartDataComboBox.setItems(FXCollections.observableArrayList(
            "Highest Selling Price",
            "Highest Buying Price", 
            "Quantity"
        ));
        chartDataComboBox.setValue("Highest Selling Price"); // Default selection
    }

    private void loadChartData() {
        String selectedOption = chartDataComboBox.getValue();
        if (selectedOption == null) return;

        try {
            productChart.getData().clear();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            
            switch (selectedOption) {
                case "Highest Selling Price":
                    series.setName("Top 10 Products by Selling Price");
                    valueAxis.setLabel("Selling Price ($)");
                    loadSellingPriceData(series);
                    break;
                case "Highest Buying Price":
                    series.setName("Top 10 Products by Buying Price");
                    valueAxis.setLabel("Buying Price ($)");
                    loadBuyingPriceData(series);
                    break;
                case "Quantity":
                    series.setName("Top 10 Products by Quantity");
                    valueAxis.setLabel("Quantity");
                    loadQuantityData(series);
                    break;
            }
            
            productChart.getData().add(series);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSellingPriceData(XYChart.Series<String, Number> series) throws Exception {
        List<Product> topProducts = DbHelper.getTopProductsBySellingPrice(10);
        for (Product product : topProducts) {
            series.getData().add(new XYChart.Data<>(product.getName(), product.getSellingPrice()));
        }
    }

    private void loadBuyingPriceData(XYChart.Series<String, Number> series) throws Exception {
        List<Product> topProducts = DbHelper.getTopProductsByBuyingPrice(10);
        for (Product product : topProducts) {
            series.getData().add(new XYChart.Data<>(product.getName(), product.getBuyingPrice()));
        }
    }

    private void loadQuantityData(XYChart.Series<String, Number> series) throws Exception {
        List<Product> topProducts = DbHelper.getTopProductsByQuantity(10);
        for (Product product : topProducts) {
            series.getData().add(new XYChart.Data<>(product.getName(), product.getQuantity()));
        }
    }
}
