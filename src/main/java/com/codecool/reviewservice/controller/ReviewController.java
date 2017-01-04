package com.codecool.reviewservice.controller;

import com.codecool.reviewservice.dao.ClientDao;
import com.codecool.reviewservice.dao.ReviewDao;
import com.codecool.reviewservice.dao.implementation.ClientDaoJdbc;
import com.codecool.reviewservice.dao.implementation.ReviewDaoJdbc;
import com.codecool.reviewservice.email.EmailAPIService;
import com.codecool.reviewservice.errorHandling.InvalidClient;
import com.codecool.reviewservice.model.Client;
import com.codecool.reviewservice.model.Review;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;


public class ReviewController {
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    private static ReviewDao reviews = ReviewDaoJdbc.getInstance();
    private static ClientDao clients = ClientDaoJdbc.getInstance();


    public static String createReview(Request request, Response response) throws IOException, URISyntaxException, InvalidClient {
        String email;

        String APIKey = request.params("APIKey");

        if (!validateClient(APIKey)) {
            throw new InvalidClient("Client is not found in database.");
        } else {
            Review newReview = new Review(getClientID(APIKey),
                    request.params("productName"),
                    request.params("comment"),
                    Integer.parseInt(request.params("ratings")));
            email = newReview.toString();
            EmailAPIService.sendReviewForModerating(email);
            return null;
        }
    }

    public static String changeStatus(Request request, Response response) throws IOException, URISyntaxException, InvalidClient {
        String APIKey = request.params("APIKey");

        if (!validateClient(APIKey)) {
            throw new InvalidClient("Client is not found in database.");
        } else {
            String reviewKey = request.params("reviewKey");
            String status = request.params("status");
            reviews.updateStatus(reviewKey, status);
            return null;
        }
    }

    public static String getAllReviewFromClient(Request request, Response response) throws IOException, URISyntaxException, InvalidClient {
        ArrayList<String> reviewsOfClient = new ArrayList<>();

        String APIKey = request.params("APIKey");
        
        if (!validateClient(APIKey)) {
            throw new InvalidClient("Client is not found in database.");
        } else {
            ArrayList<Review> returnReviews = reviews.getApprovedByClientId(getClientID(APIKey));
            for (Review review : returnReviews) {
                reviewsOfClient.add(review.toString());
            }
            return jsonify(reviewsOfClient);
        }
    }

    public static String getAllReviewOfProduct(Request request, Response response) throws IOException, URISyntaxException, InvalidClient {
        String APIKey = request.params("APIKey");
        String productName = request.params("productName");
        ArrayList<String> approvedReviews = new ArrayList<>();;

        int clientID = (Integer)clients.getByAPIKey(APIKey).getId();

        if (clientID == 0) {
            throw new InvalidClient("Client not found in database");
            return null;
        } else {
            ArrayList<Review> returnReviews = reviews.getApprovedReviewsBy(productName);
            for (Review review : returnReviews) {
                approvedReviews.add(review.toString());
            }
            return jsonify(approvedReviews);
        }
    }

    private static boolean validateClient(String APIKey) {
        Client client = clients.getByAPIKey(APIKey);
        if (client == null) {
            return false;
        }
        return true;
    }

    private static int getClientID(String APIKey){
        return clients.getByAPIKey(APIKey).getId();
    }

    private static String jsonify(ArrayList<String> list) {
        return new Gson().toJson(list);
    }

}


//:APIKey/:productName/:comment/:rating"
