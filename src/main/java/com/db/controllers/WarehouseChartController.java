//q1
package com.db.controllers;

import com.db.database.DbHelper;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.util.Map;

public class WarehouseChartController {

    @FXML
    private BarChart<String, Number> warehouseBarChart;

    @FXML
    public void initialize() {
        loadChartData();
    }

    private void loadChartData() {
        Map<String, Integer> locationData = DbHelper.getWarehouseCountByLocation();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Warehouse Count");

        for (Map.Entry<String, Integer> entry : locationData.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        warehouseBarChart.getData().add(series);
    }
}
