package com.codecool.reviewservice.dao;

import com.codecool.reviewservice.model.Review;

public interface ReviewDao {
    void add(Review reviewModel);
    void remove(int id);
    Review getByClientID(int clientID);
    Review getByProductName(String productName);
}
