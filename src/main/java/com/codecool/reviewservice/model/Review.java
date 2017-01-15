package com.codecool.reviewservice.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Review class contains everything related to Review objects.
 * @author Jani
 */
public class Review {
    private static final Logger logger = LoggerFactory.getLogger(Review.class);

    private String id;
    private int clientID;
    private String productName;
    private String comment;
    private int rating;
    private String status;
    private String reviewKey;

    /**
     * This constructor is used when a new review is submitted to the Review Service and a Review object is
     * initialized for the first time, based on the data coming from the clients' web pages. This means that
     * the review doesn't have a hash (=reviewKey) in the database yet, so this constructor assigns one to it.
     * The status of a newly created Review object is always PENDING.
     * @param clientID      The ID of the client.
     * @param productName   Name of the product that is an identifier of the product which is commonly used
     *                      on all the clients's sites who sell the same product.
     * @param comment       The comment/review itself that users left under a product on the client's web site.
     * @param rating        An integer between 0-5.
     */
    public Review(int clientID, String productName, String comment, int rating) {
        this.clientID = clientID;
        this.productName = handleProductName(productName);
        this.comment = comment;
        this.rating = rating;
        this.status = Status.PENDING.toString();
        this.reviewKey = UUID.randomUUID().toString();

        logger.info("Create new Review model with new reviewKey: " + this.reviewKey + " Status: " + this.status);
    }

    /**
     * This constructor is used when a hash (reviewKey) has been already assigned to the rieview.
     * * The status of a newly created Review object is always PENDING.
     * @param clientID      The ID of the client
     * @param productName   Name of the product that is an identifier of the product which is commonly used
     *                      on all the clients's sites who sell the same product.
     * @param comment       The comment/review itself that users left under a product on the client's web site.
     * @param rating        An integer between 0-5.
     * @param reviewKey     An identifier for a review object in the database.
     * @param status        Status of the modaration. Defined in the Status enum.
     * @see                 Status
     * @see                 Client
     */
    public Review(int clientID, String productName, String comment, int rating, String reviewKey, String status) {
        this.clientID = clientID;
        this.productName = productName;
        this.comment = comment;
        this.rating = rating;
        this.reviewKey = reviewKey;
        this.status = status;

        logger.info("Create Review model with the reviewKey: " + this.reviewKey + " Status: " + this.status);

    }

    /**
     * This method returns the moderation status of the review.
     * @return  The status of the review as a String.
     * @see     Status
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method sets the status of the Review object to APPROVED.
     */
    public void setStatusApproved() {
        logger.info("Set status to: " + Status.APPROVED);
        this.status = Status.APPROVED.toString();
    }

    /**
     * This method sets the status of the Review object to DENIED.
     */
    public void setStatusDenied() {
        logger.info("Set status to: " + Status.DENIED);
        this.status = Status.DENIED.toString();
    }

    /**
     * This method is responsible for formatting the name of the products. It removes any whitespaces and concatenates
     * the words.
     * @param prodName  not null
     * @return          The formatted product name as a String
     */
    private String handleProductName(String prodName){
        return prodName.replace(" ", "").toUpperCase();
    }

    /**
     * This method returns the product name.
     * @return The name of the product which is the review about.
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the id of the review object.
     * @param id
     */
    public void setId(String id){
        this.id = id;
    }

    /**
     * This method returns the id of the object.
     * @return The id as a String.
     */
    public String getId() {
        return id;
    }

    /**
     * This method returns the client id of the object. This id belongs to the Client/Web shop where the review has been
     * submitted.
     * @return  The id as a String.
     * @see     Client
     */
    public int getClientID() {
        return clientID;
    }

    /**
     * This method returns the actual comment/review that a user of the webshop wrote on a product in a webshop.
     * @return
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method returns the rating of the review.
     * @return The rating as a String
     */
    public int getRating() {
        return rating;
    }

    /**
     * Returns the reviewKey.
     * @return  The reviewKey as an String.
     */
    public String getReviewKey() {
        return reviewKey;
    }

    /**
     * This method converts the review object into String.
     * @return The review object as a String.
     */
    @Override
    public String toString(){
        return String.format(
                        "Client_id: %s \n" +
                        "Product name: %s \n" +
                        "Review: %s \n" +
                        "Ratings: %d \n" +
                        "Review Key: %s \n" +
                        "Status: %s", getClientID(),getProductName(),getComment(),getRating(),getReviewKey(),getStatus());
    }
}
