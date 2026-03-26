package com.db.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    public static Connection connect() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://127.0.0.1:3306/warehousesystem?user=root&useSSL=false&allowPublicKeyRetrieval=true";
        String user = "root";
        String pass = "tasneemsql";

        Class.forName("com.mysql.jdbc.Driver"); // You're using connector 5.1.47
        return DriverManager.getConnection(url, user, pass);
    }
}
