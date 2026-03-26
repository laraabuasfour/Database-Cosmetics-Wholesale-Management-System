package com.db;

import java.sql.Connection;

import com.db.database.DB;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainDashboard.fxml"));
        Parent root = loader.load();

        // Set the scene and show the stage
        primaryStage.setTitle("Warehouse Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        testConnection();
        launch(args);
    }

    public static void testConnection() {
        try {
            Connection conn = DB.connect();
            System.out.println(" Connected successfully!");
            conn.close();
        } catch (Exception e) {
            System.out.println(" Connection failed: " + e.getMessage());
        }
    }
}
