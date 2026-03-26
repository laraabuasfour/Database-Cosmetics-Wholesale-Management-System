package com.db.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.db.database.DbHelper;
import com.db.models.Employee;

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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class EmployeeTableController {

    @FXML
    private Button addButton;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private Button clearButton;

    @FXML
    private TableColumn<Employee, Integer> eid;

    @FXML
    private TableColumn<Employee, String> ename;

    @FXML
    private TableColumn<Employee, BigDecimal> salary;

    @FXML
    private TableColumn<Employee, String> phoneNum;

    @FXML
    private TableColumn<Employee, LocalDate> birthDate;

    @FXML
    private TableColumn<Employee, String> role;

    @FXML
    private TableColumn<Employee, Void> actions;

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private BarChart<String, Number> ordersChart;

    @FXML
    private CategoryAxis monthAxis;

    @FXML
    private NumberAxis orderCountAxis;

    private ObservableList<Employee> employeeList = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws Exception {
        eid.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        ename.setCellValueFactory(new PropertyValueFactory<>("ename"));
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        phoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        birthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));

        actions.setCellFactory(col -> new TableCell<Employee, Void>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox box = new HBox(5, editButton, deleteButton);

            {
                box.setAlignment(Pos.CENTER);

                editButton.setOnAction(e -> {
                    Employee employee = getTableView().getItems().get(getIndex());
                    openCreateUpdateScene(true, employee);
                });

                deleteButton.setOnAction(e -> {
                    Employee employee = getTableView().getItems().get(getIndex());
                    showDeleteConfirmationDialog(employee);
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

        employeeTable.setItems(employeeList);
        setupChart();
        refreshTable();
        loadChartData();
    }

    @FXML
    void onClick(ActionEvent event) throws Exception {
        openCreateUpdateScene(false, null);
    }

    @FXML
    void onSearch(ActionEvent event) throws Exception {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            refreshTable();
        } else {
            searchEmployees(searchText);
        }
    }

    @FXML
    void onClear(ActionEvent event) throws Exception {
        searchField.clear();
        refreshTable();
    }

    private void refreshTable() throws Exception {
        employeeList.clear();
        List<Employee> employees = DbHelper.getAllEmployees();
        employeeList.setAll(employees);
    }

    private void searchEmployees(String searchName) throws Exception {
        employeeList.clear();
        List<Employee> employees = DbHelper.searchEmployeesByName(searchName);
        employeeList.setAll(employees);
    }

    private void openCreateUpdateScene(boolean isUpdate, Employee employee) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EmployeeCreateUpdate.fxml"));
            Parent root = loader.load();

            EmployeeInfoSaveController controller = loader.getController();
            controller.setUpdate(isUpdate);
            if (isUpdate) {
                controller.setEmployeeToUpdate(employee);
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
            stage.setTitle(isUpdate ? "Edit Employee" : "Add New Employee");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showDeleteConfirmationDialog(Employee employee) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Employee");
        alert.setHeaderText("Are you sure you want to delete this employee?");
        alert.setContentText("Employee: " + employee.getEname() + " will be deleted.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                deleteEmployee(employee);
            }
        });
    }

    private void deleteEmployee(Employee employee) {
        try {
            DbHelper.deleteEmployee(employee.getEmployeeId());
            refreshTable();
            System.out.println("Employee deleted: " + employee.getEname());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupChart() {
        ordersChart.setTitle("Top 5 Employees - Orders Per Month (Last 6 Months)");
        monthAxis.setLabel("Month");
        orderCountAxis.setLabel("Number of Orders");
        ordersChart.setLegendVisible(true);
    }

    private void loadChartData() {
        try {
            List<Map<String, Object>> chartData = DbHelper.getEmployeeOrdersPerMonth();
            
            // Group data by employee
            Map<String, Map<String, Integer>> employeeData = new HashMap<>();
            
            for (Map<String, Object> data : chartData) {
                String employeeName = (String) data.get("employeeName");
                String monthYear = (String) data.get("monthYear");
                Integer orderCount = (Integer) data.get("orderCount");
                
                if (monthYear != null) { // Only include months with actual orders
                    employeeData.computeIfAbsent(employeeName, k -> new HashMap<>())
                              .put(monthYear, orderCount);
                }
            }
            
            // Get all unique months and sort them
            Set<String> allMonths = new TreeSet<>();
            for (Map<String, Integer> monthData : employeeData.values()) {
                allMonths.addAll(monthData.keySet());
            }
            
            // Clear existing data
            ordersChart.getData().clear();
            
            // Create series for each employee
            for (Map.Entry<String, Map<String, Integer>> entry : employeeData.entrySet()) {
                String employeeName = entry.getKey();
                Map<String, Integer> monthData = entry.getValue();
                
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName(employeeName);
                
                // Add data points for each month
                for (String month : allMonths) {
                    Integer count = monthData.getOrDefault(month, 0);
                    series.getData().add(new XYChart.Data<>(month, count));
                }
                
                ordersChart.getData().add(series);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
