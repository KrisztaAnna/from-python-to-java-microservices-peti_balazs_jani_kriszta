package com.codecool.reviewservice.dao;

import com.codecool.reviewservice.model.Review;

import java.util.ArrayList;

/**
 * ReviewDao is an interface which provided specific data operations on the Review model.
 * Find further comments on its methods in its in implementation in the ReviewDaoJdbc class.
 */
public interface ReviewDao {
    void add(Review reviewModel);
    void remove(int id);
    ArrayList<Review> getByClientID(int clientID);
    ArrayList<Review> getByProductName(String productName);
    ArrayList<Review> getApprovedByClientId(int clientID);
    ArrayList<Review> getApprovedByProductName(String productName);
    void updateStatus(String review_key, String newStatus);
}
