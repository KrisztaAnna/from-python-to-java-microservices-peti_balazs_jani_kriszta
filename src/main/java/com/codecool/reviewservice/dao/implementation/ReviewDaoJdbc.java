package com.codecool.reviewservice.dao.implementation;

import com.codecool.reviewservice.dao.Connection.DBConnection;
import com.codecool.reviewservice.dao.ReviewDao;
import com.codecool.reviewservice.model.Review;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReviewDaoJdbc implements ReviewDao {
    private DBConnection connection = new DBConnection();
    private static ReviewDaoJdbc instance = null;
    private String sql;

    public static ReviewDaoJdbc getInstance(){
        if (instance == null){
            instance = new ReviewDaoJdbc();
        }
        return instance;
    }

    public void add(Review reviewModel) {
        int clientId = getClientId(reviewModel.getClientID());
        String productName = reviewModel.getProductName();
        String comment = reviewModel.getComment();
        int ratings = reviewModel.getRating();
        String reviewKey = reviewModel.getReviewKey();

        sql = "INSERT INTO review (client_id, product_name, comment, ratings, review_key) " +
                "VALUES("+clientId+",'"+productName+"','"+comment+"',"+ratings+",'"+reviewKey+"');";
        connection.executeQuery(sql);
    }

    public void remove(int id) {

    }

    public Review getByClientID(int clientID) {
        sql = "SELECT * FROM review WHERE id="+clientID+";";
        return createReviewModel(sql);
    }

    public Review getByProductName(String productName) {
        sql = "SELECT * FROM review WHERE id='"+productName+"';";
        return createReviewModel(sql);
    }
//    TODO: MODIFY the return to client object
    private int getClientId(int clientId){
        sql = "SELECT id FROM client WHERE id="+clientId+";";
        try (Connection conn = connection.connect();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql)){
            if (rs.next()){
                return rs.getInt("id");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    private Review createReviewModel(String sql){
        try (Connection conn = connection.connect();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql)){
            if (rs.next()){
                Review review = new Review(rs.getInt("client_id"), rs.getString("product_name"),
                        rs.getString("comment"), rs.getInt("ratings"), rs.getString("review_key"));
                review.setId(rs.getString("id"));
                return review;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
