package com.codecool.reviewservice;

import com.codecool.reviewservice.controller.ReviewController;

import static spark.Spark.*;

public class Server {

    public static void main(String[] args) {
        // Connection to PostgreSQL Database
        DBConnection dbConnection = new DBConnection();
        dbConnection.connect();

        // Default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        // Routes
        get("review/:APIKey/:productName/:comment/:rating", ReviewController.createReview);
        get("changeStatus/:reviewKey/:status", ReviewController.changeStatus);
        get("reviewFromClient/:APIKey", ReviewController.getAllREviewFromClient);
        get("allReviewOfProduct/:ProductName", ReviewController.getAllReviewOfProduct);
    }
}
