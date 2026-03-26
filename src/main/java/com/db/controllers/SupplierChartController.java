//Q1
package com.db.controllers;
import com.db.database.DB;
import com.db.database.DbHelper;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

public class SupplierChartController {
    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    public void initialize() {
        String query = "SELECT location, COUNT(*) AS total_suppliers FROM suppliers GROUP BY location ORDER BY total_suppliers DESC";

        try (Connection conn = DB.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Suppliers per Location");

            while (rs.next()) {
                String location = rs.getString("location");
                int count = rs.getInt("total_suppliers");
                series.getData().add(new XYChart.Data<>(location, count));
            }

            barChart.getData().add(series);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
