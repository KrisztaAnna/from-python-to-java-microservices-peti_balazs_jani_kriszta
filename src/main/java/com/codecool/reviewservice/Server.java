package com.codecool.reviewservice;

//import com.codecool.reviewservice.controller.Controller;
import com.codecool.reviewservice.controller.ReviewController;
import com.codecool.reviewservice.dao.connection.DBConnection;

import java.sql.SQLException;

import static spark.Spark.*;

public class Server {

    public static void main(String[] args) throws SQLException {
        // connection to PostgreSQL Database
        DBConnection dbConnection = new DBConnection();
        dbConnection.connect();

        // Default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        // Routes
        get("/review/:APIKey/:productName/:comment/:ratings", (request, response) -> ReviewController.createReview(request, response));
        get("/changeStatus/:APIKey/:reviewKey/:status", (request, response) -> ReviewController.changeStatus(request, response));
        get("reviewFromClient/:APIKey", (request, response) -> ReviewController.getAllReviewFromClient(request, response));
        get("allReviewOfProduct/:APIKey/:ProductName", (request, response) -> ReviewController.getAllReviewOfProduct(request, response));
    }
}
