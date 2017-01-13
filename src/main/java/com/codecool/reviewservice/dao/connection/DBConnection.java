package com.codecool.reviewservice.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Handles the connection to the PostgreSQL database and the execution of queries.
 * To be able to use the application properly, first you need to create a file (connection.properties) in the 'resources' directory,
 * and provide the following data: url=jdbc:postgresql://localhost:5432/review_service, user=yourPostgreSQLusername,
 * password=yourPostgreSQLpassword
 *
 * @author Kriszta
 */
public class DBConnection {
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    /**
     * Reads the PostgreSQL database info from connection.properties, and assigns them to the class attributes.
     */
    public DBConnection() {
        ResourceBundle rb = ResourceBundle.getBundle("connection"); // connection.properties
        URL = rb.getString("url");
        USER = rb.getString("user");
        PASSWORD = rb.getString("password");
    }

    /**
     * Creates the database connection, using the class attributes as parameters.
     * @return Returns the db connection.
     * @throws SQLException
     * @see    SQLException
     */
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * This method executes the SQL queries throughout the application by creating a Statement object and
     * then calling the execute method on it with the query (as a String) passed to it as an argument.
     * @param query An SQL query as a string.
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