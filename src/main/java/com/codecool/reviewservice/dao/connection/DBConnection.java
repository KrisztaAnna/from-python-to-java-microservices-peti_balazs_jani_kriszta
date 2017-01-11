package com.codecool.reviewservice.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Handles the connection to the PostgreSQL database and the execution of queries.
 * @author Kriszta
 */
public class DBConnection {
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    /**
     * Reads the postgres database info from connection.properties, and assigns them to the class attributes.
     */
    public DBConnection() {
        ResourceBundle rb = ResourceBundle.getBundle("connection"); // connection.properties
        URL = rb.getString("url");
        USER = rb.getString("user");
        PASSWORD = rb.getString("password");
    }

    /**
     * Creates the database connection, using the class attributes as parameters.
     * @return connection Returns the db connection.
     * @throws SQLException
     * @see SQLException
     */
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * This method creates a Statement object and then calls the execute method on it with the query passed to it
     * as a parameter. Throws
     * @param query String of the SQL query.
     */
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