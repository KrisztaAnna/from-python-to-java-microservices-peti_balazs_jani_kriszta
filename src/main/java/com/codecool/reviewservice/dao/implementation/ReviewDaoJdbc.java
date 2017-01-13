package com.codecool.reviewservice.dao.implementation;

import com.codecool.reviewservice.dao.connection.DBConnection;
import com.codecool.reviewservice.dao.ReviewDao;
import com.codecool.reviewservice.model.Client;
import com.codecool.reviewservice.model.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
* This class implements the methods of the RieviewDao interface. The class ReviewDaoJdbc is a singleton.
* @author Jani
*/
public class ReviewDaoJdbc implements ReviewDao {
    private static final Logger logger = LoggerFactory.getLogger(ReviewDaoJdbc.class);
    private DBConnection connection = new DBConnection();
    private static ReviewDaoJdbc instance = null;
//    public ArrayList<Review> reviews = new ArrayList<>();
    private String sql;

    /**
     * This method returns the instance of the ClientDaoJdbc singleton if it already exists. If not it instantiates
     * it and then returns it.
     * @return   the instance of class ReviewDaoJdbc
     */
    public static ReviewDaoJdbc getInstance(){
        if (instance == null){
            instance = new ReviewDaoJdbc();
            logger.info("Create a new ReviewDaoJdbc Singleton "+instance);
        }
        return instance;
    }

    /**
     * This method implements the add method of the ReviewDao interface. It is for adding a new record to the Review
     * model in the database. The method takes a Review object as an argument and then executes the query which adds
     * it to the database.
     * @param  reviewModel  a Review object
     * @see                 Review
     */
    public void add(Review reviewModel) {
        int clientId = getClientId(reviewModel.getClientID());
        String productName = reviewModel.getProductName();
        String comment = reviewModel.getComment();
        int ratings = reviewModel.getRating();
        String reviewKey = reviewModel.getReviewKey();
        String status = reviewModel.getStatus();

        sql = "INSERT INTO review (client_id, product_name, comment, ratings, review_key, status) " +
                "VALUES("+clientId+",'"+productName+"','"+comment+"',"+ratings+",'"+reviewKey+"', '"+status+"');";
        connection.executeQuery(sql);
        logger.debug("Save to database | Review model: "+reviewModel);
    }

    /**
     * This method implements the remove method of the ReviewDao interface. It is for removing a record from the Review
     * model in the database. The method takes a reviewKey as a string as an argument and then executes the query which
     * removes the record with the matching reviewKey from the database.
     * @param reviewKey an unique hash belongs to every Review record in the database, this is the reviewKey
     */
    public void remove(int reviewKey) {
        sql = "DELETE FROM review WHERE review_key='"+reviewKey+"';";
        executeQuery(sql);
        logger.debug("Delete from database | ReviewKey: "+reviewKey);
    }

     /**
     * This method implements the getByClientID method of the ReviewDao interface. It is for returning a record from
     * the Review model in the database by taking a client ID as an argument and then executing the query
     * which returns the Review records with the matching client ID from the database.
     * @param clientID an ID of a client
     * @return         an ArrayList of Review objects
     * @see            Client
     */
    public ArrayList<Review> getByClientID(int clientID) {
        sql = "SELECT * FROM review WHERE id="+clientID+";";
        logger.debug("Get a review by client_id("+clientID+") | Review model: "+createReviewModel(sql));
        return createReviewModel(sql);
    }

    /**
     * This method implements the getByProductName method of the ReviewDao interface. It is for returning a record from
     * the Review model in the database by taking a product name as an argument and then executing the query
     * which returns the Review records with the matching product name from the database.
     * @param productName the name of the product we want to find the reviews of
     * @return            an ArrayList of Review objects
     */
    public ArrayList<Review> getByProductName(String productName) {
        sql = "SELECT * FROM review WHERE product_name='"+productName+"';";
        logger.debug("Get a review by product_name("+productName+") | Review model: "+createReviewModel(sql));
        return createReviewModel(sql);
    }

    /**
     * Returns an ArrayList of Review objects. Takes a productName as an argument and returns all the approved reviews
     * of that particular product from all the clients.
     * @param productName the name of a product
     * @return            ArrayList of Review objects
     */
    public ArrayList<Review> getApprovedByProductName(String productName) {
        sql = "SELECT * FROM review WHERE product_name='"+productName+"' and status='APPROVED';";
        logger.debug("Get a review by product_name "+productName+" if status is APPROVED | Review model: "+createReviewModel(sql));
        return createReviewModel(sql);
    }

    /**
     * Returns an ArrayList of Review objects. Takes a client ID as an argument and returns all the approved reviews
     * submitted on the client's web page.
     * @param clientID the ID of a client
     * @return         ArrayList of Review objects
     * @see            Client
     */
    public ArrayList<Review> getApprovedByClientId(int clientID) {
        sql = "SELECT * FROM review WHERE client_id="+clientID+" and status='APPROVED';";
        logger.debug("Get a review by client_id "+clientID+" if status is APPROVED | Review model: "+createReviewModel(sql));
        return createReviewModel(sql);
    }

    /**
     * This method is used for updating the status if the reviews. The first argument it takes is a review key which is
     * used for finding the actual record in database table, and the second one is the new status we want to set.
     * @param reviewKey an unique hash belongs to every Review record in the database, this is the reviewKey
     * @param newStatus
     */
    public void updateStatus(String reviewKey, String newStatus){
        sql = "UPDATE review SET status='"+newStatus+"' WHERE review_key='"+reviewKey+"';";
        executeQuery(sql);
        logger.info("Update review status where review_key: "+reviewKey+" | new status: "+newStatus);
    }

    // TODO: find out what this method is really for
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

    /**
     * Takes an SQL query as an argument. When called it connects to the database, instantiates a Review object,
     * sets it's ID and then returns in an ArrayList.
     * @param sql an SQL query as a String
     * @return    an ArrayList containing the new Review object.
     */
    private ArrayList<Review> createReviewModel(String sql){
        ArrayList<Review> reviews = new ArrayList<>();
        try (Connection conn = connection.connect();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql)){
            if (rs.next()) {
                Review review = new Review(rs.getInt("client_id"),
                        rs.getString("product_name"),
                        rs.getString("comment"),
                        rs.getInt("ratings"),
                        rs.getString("review_key"),
                        rs.getString("status"));
                review.setId(rs.getString("id"));
                reviews.add(review);
            }
                return reviews;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Uses the Connection object's executeQuery() method to execute the SQL queries passed it as an argument.
     * @param sql an SQL query as a String
     */
    private void executeQuery(String sql){
        connection.executeQuery(sql);
    }
}
