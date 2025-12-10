package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private final String URL = "jdbc:postgresql://localhost:5432/product_management_db";
    private final String user = "product_manager_user";
    private final String password = "123456";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, user, password);
    }
}
