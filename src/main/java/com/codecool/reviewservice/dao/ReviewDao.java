package com.codecool.reviewservice.dao;

import com.codecool.reviewservice.model.Review;

import java.util.ArrayList;

/**
 * ReviewDao is an interface which provides methods for specific data operations on the Review model.
 * Find further comments on its methods along their implementations in the ReviewDaoJdbc class.
 * @author Jani
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
