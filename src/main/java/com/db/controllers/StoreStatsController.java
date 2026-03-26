package com.db.controllers;

import com.db.database.DbHelper;
import com.db.models.StoreStats;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class StoreStatsController {
    @FXML
    private TableView<StoreStats> statsTable;

    @FXML
    private TableColumn<StoreStats, String> locationCol;

    @FXML
    private TableColumn<StoreStats, Integer> countCol;

    @FXML
    private PieChart pieChart;

    @FXML
    public void initialize() throws Exception {
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        countCol.setCellValueFactory(new PropertyValueFactory<>("totalStores"));

        List<StoreStats> stats = DbHelper.getStoreCountPerLocation();
        ObservableList<StoreStats> observableStats = FXCollections.observableArrayList(stats);
        statsTable.setItems(observableStats);

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for (StoreStats s : stats) {
            pieData.add(new PieChart.Data(s.getLocation(), s.getTotalStores()));
        }
        pieChart.setData(pieData);
        pieChart.setTitle("Number of stores by location");
    }

}