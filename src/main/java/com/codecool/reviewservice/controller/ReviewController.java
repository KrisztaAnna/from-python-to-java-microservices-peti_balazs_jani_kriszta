package com.codecool.reviewservice.controller;

import com.codecool.reviewservice.dao.ClientDao;
import com.codecool.reviewservice.dao.ReviewDao;
import com.codecool.reviewservice.dao.implementation.ClientDaoJdbc;
import com.codecool.reviewservice.dao.implementation.ReviewDaoJdbc;
import com.codecool.reviewservice.email.Email;
import com.codecool.reviewservice.errorHandling.InvalidClient;
import com.codecool.reviewservice.model.Client;
import com.codecool.reviewservice.model.Review;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * This class is for controlling everything related to product reviews.
 *
 * @author Kriszta
 */
public class ReviewController {
    private static ReviewDao reviews = ReviewDaoJdbc.getInstance();
    private static ClientDao clients = ClientDaoJdbc.getInstance();

    /**
     * The "/review/:APIKey/:productName/:comment/:ratings" route triggers newReview() which handles new reviews
     * submitted by users of the web shops.
     * First it runs a validation whether the API key of the shop is valid by calling the validateClient() method.
     * If the API key is valid it creates a new Review object, adds it to the database and also passes it to
     * a method from the Email class, called ReviewForModerationEmail(), which sends an email to the client.
     * If the API key is invalid the method throws an InvalidClient exception.
     * @param request  A Spark request object
     * @param response A Spark response object
     * @return         null
     * @throws IOException
     * @throws URISyntaxException
     * @throws InvalidClient
     * @see IOException
     * @see URISyntaxException
     * @see InvalidClient
     * @see Email
     * @see Review
     */
    public static String newReview(Request request, Response response) throws IOException, URISyntaxException, InvalidClient {
        String APIKey = request.params("APIKey");

        if (!validateClient(APIKey)) {
            throw new InvalidClient("Client is not found in database.");
        } else {
            Review newReview = new Review(getClientID(APIKey),
                                          request.params("productName"),
                                          request.body(),
                                          Integer.parseInt(request.params("ratings")));
            reviews.add(newReview);
            Email.ReviewForModerationEmail(newReview);
            return null;
        }
    }

    /**
     * The changeStatus() method is responsible for switching the status of the reviews in the database, it is triggered by the
     * "/changeStatus/:APIKey/:reviewKey/:status" route in the Server.
     * By default every review is marked as PENDING and they can be set APPROVED or DENIED by the links provided in the emails
     * sent to the clients.
     * First the method runs a validation whether the API key is valid by calling the validateClient() method.
     * If the API key is valid it sets the status of the review and redirects the user to the "/newstatus" route.
     * If the API key is invalid the method throws an InvalidClient exception.
     * @param request A Spark request object
     * @param response A Spark response object
     * @return null
     * @throws IOException
     * @throws URISyntaxException
     * @throws InvalidClient
     * @see IOException
     * @see URISyntaxException
     * @see InvalidClient
     */
    public static String changeStatus(Request request, Response response) throws IOException, URISyntaxException, InvalidClient {
        String APIKey = request.params("APIKey");

        if (!validateClient(APIKey)) {
            throw new InvalidClient("Client is not found in database.");
        } else {
            String reviewKey = request.params("reviewKey");
            String status = request.params("status").toUpperCase();
            reviews.updateStatus(reviewKey, status);
            response.redirect("/newStatus");
            return null;
        }
    }

    /**
     * This method is used for returning all approved reviews submitted on the client's web page as a JSON string.
     * It is called through the "/reviewFromClient/:APIKey" route.
     * Throws an InvalidClient exception if the API Key provided by the client is invalid.
     * @param request A Spark request object
     * @param response A Spark response object
     * @return String
     * @throws IOException
     * @throws URISyntaxException
     * @throws InvalidClient
     * @see IOException
     * @see URISyntaxException
     * @see InvalidClient
     * @see Review
     */
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

    /**
     * This method is used for returning all approved reviews of a specific product from the database as a JSON string.
     * It is called through the "/allReviewOfProduct/:APIKey/:ProductName" route.
     * Throws an InvalidClient exception if the API Key provided by the client is invalid.
     * @param request A Spark request object
     * @param response A Spark response object
     * @return String Returns all approved Review objects of a specific product as a JSON string.
     * @throws IOException
     * @throws URISyntaxException
     * @throws InvalidClient
     * @see IOException
     * @see URISyntaxException
     * @see InvalidClient
     */
    public static String getAllReviewOfProduct(Request request, Response response) throws IOException, URISyntaxException, InvalidClient {
        String APIKey = request.params("APIKey");
        String productName = request.params("productName");

        ArrayList<String> approvedReviews = new ArrayList<>();

        if (!validateClient(APIKey)) {
            throw new InvalidClient("Client is not found in database.");
        } else {
            ArrayList<Review> returnReviews = reviews.getApprovedByProductName(productName.replace(" ", "").toUpperCase());
            for (Review review : returnReviews) {
                approvedReviews.add(review.toString());
            }
            return jsonify(approvedReviews);
        }
    }

    /**
     * This method is used for validating the clients by their API Key. If the API key is not in the database the method returns false,
     * if it is in the database, the method returns true.
     * @param APIKey an unique hash belongs to every Client record in the database, this is the APIKey
     * @return Boolean
     */
    private static boolean validateClient(String APIKey) {
        Client client = clients.getByAPIKey(APIKey);
        if (client == null) {
            return false;
        }
        return true;
    }

    /**
     * This method is used for getting a specific client's ID by their API Key.
     * @param APIKey an unique hash belongs to every Client record in the database, this is the APIKey
     * @return int Returns the ID of a client.
     */
    private static int getClientID(String APIKey){
        return clients.getByAPIKey(APIKey).getId();
    }

    /**
     * This method is used for converting an ArrayList (which contains Review objects as strings) into JSON.
     * @param reviews Review objects as strings in an ArrayList
     * @return a JSON string of review objects.
     */
    private static String jsonify(ArrayList<String> reviews) {
        return new Gson().toJson(reviews);
    }

}