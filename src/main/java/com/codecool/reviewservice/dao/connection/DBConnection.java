package com.codecool.reviewservice.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DBConnection {
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    public DBConnection() {
        ResourceBundle rb = ResourceBundle.getBundle("connection"); // connection.properties
        URL = rb.getString("url");
        USER = rb.getString("user");
        PASSWORD = rb.getString("password");
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void executeQuery(String query) {
        try (Connection connection = connect();
             Statement statement =  connection.createStatement()
        ){
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}