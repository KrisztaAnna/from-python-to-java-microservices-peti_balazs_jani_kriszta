package com.codecool.reviewservice.controller;

import com.codecool.reviewservice.errorHandling.InvalidClient;
import com.codecool.reviewservice.dao.ClientDao;
import com.codecool.reviewservice.dao.ReviewDao;
import com.codecool.reviewservice.dao.implementation.ClientDaoJdbc;
import com.codecool.reviewservice.dao.implementation.ReviewDaoJdbc;
import com.codecool.reviewservice.email.EmailAPIService;
import com.codecool.reviewservice.model.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.net.URISyntaxException;


public class ReviewController {
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    private static ReviewDao reviews = ReviewDaoJdbc.getInstance();
    private static ClientDao clients = ClientDaoJdbc.getInstance();


    public static String createReview(Request request, Response response) throws IOException, URISyntaxException, InvalidClient {
        String email;
        String APIKey = request.params("APIKey");
        String productName = request.params("productName");
        String comment = request.params("comment");
        int ratings =  Integer.parseInt(request.params("ratings"));
        int clientID = reviews.getBy(APIKey);

        if (clientID == 0) {
            throw new InvalidClient("Client not found");
            return null;
        } else {
            Review newReview = new Review(clientID, productName, comment, ratings);
            email = newReview.toString();
            EmailAPIService.sendReviewForModerating(email);
            return null;
        }
    }


    public static String changeStatus(Request request, Response response) throws IOException, URISyntaxException, InvalidClient {
        Review moderatedReview;

        String APIKey = request.params("APIKey");
        String reviewKey = request.params("reviewKey");
        String status = request.params("status");

        int clientID = reviews.getBy(APIKey);

        if (clientID == 0) {
            throw new InvalidClient("Client not found");
            return null;
        } else {
            moderatedReview = reviews.getBy(reviewKey);
            moderatedReview.setStatus(status);
            return null;
        }
    }

    public static String getAllReviewFromClient(Request request, Response response) throws IOException, URISyntaxException, InvalidClient {

        return null;
    }

    public static String getAllReviewOfProduct(Request request, Response response) throws IOException, URISyntaxException, InvalidClient {
        return null;
    }

}


//:APIKey/:productName/:comment/:rating"
