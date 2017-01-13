package com.codecool.reviewservice.dao.implementation;

import com.codecool.reviewservice.dao.ClientDao;
import com.codecool.reviewservice.dao.connection.DBConnection;
import com.codecool.reviewservice.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class implements the methods of the ClientDao interface. The class ClientDaoJdbc is a singleton.
 * @author Peti
 */
public class ClientDaoJdbc implements ClientDao {
    private static final Logger logger = LoggerFactory.getLogger(ClientDaoJdbc.class);
    private DBConnection connection = new DBConnection();
    private static ClientDaoJdbc instance = null;
    private String sql;

    /**
     * This method is for getting or creating and and then returning the instance of the ClientDaoJdbc class.
     * @return   the instance of the ClientDaoJdbc class
     */
    public static ClientDaoJdbc getInstance(){
        if(instance == null){
            instance = new ClientDaoJdbc();
            logger.info("New ClientDaoJdbc instance created: {}", instance);
        }
        return instance;
    }

    /**
     * This method implements the add method of the ClientDao interface. It is for adding a new record to the Client model
     * in the database. The method takes a client object as an argument and then executes the query which adds it to the database.
     * @param  clientModel  a Client object
     * @see                 Client
     */
    @Override
    public void add(Client clientModel) {
        String APIKey = clientModel.getAPIKey();
        String name = clientModel.getName();
        String email = clientModel.getEmail();

        sql = "INSERT INTO client (api_key, name, email)" +
                "VALUES('" + APIKey + "', '" + name + "', '" + email + "');";
        logger.debug("Saving to database: {}", clientModel);
        executeQuery(sql);
    }

    /**
     * This method implements the remove method of the ClientDao interface. It is for removing a record from the Client model
     * in the database. The method takes an APIKey string as an argument and then executes the query which removes the record
     * with the matching APIKey from the database.
     * @param APIKey an unique hash belongs to every Client record in the database, this is the APIKey
     */
    @Override
    public void remove(String APIKey) {
        sql = "DELETE FROM client WHERE api_key='" + APIKey + "';";
        logger.debug("Deleting client with API key {}", APIKey);
        executeQuery(sql);
    }

    /**
     * This method implements the getById method of the ClientDao interface. It is for returning a record from the Client model
     * in the database. The method takes an ID integer as an argument and then executes the query which returns the record with
     * the matching ID from the database.
     * @param id an ID integer
     * @return   a Client object
     * @see      Client
     */
    @Override
    public Client getById(int id) {
        sql = "SELECT * FROM client WHERE id='" + id + "';";
        logger.debug("Selecting client with id {}", id);
        return createClientModel(sql);
    }

    /**
     * This method implements the getByAPIKey method of the ClientDao interface. It is for returning a record from the Client model
     * in the database. The method takes an ID integer as an argument and then executes the query which returns the record with
     * the matching ID from the database.
     * @param APIKey an unique hash belongs to every Client record in the database, this is the APIKey
     * @return       a Client object
     * @see          Client
     */
    @Override
    public Client getByAPIKey(String APIKey) {
        sql = "SELECT * FROM client WHERE api_key='" + APIKey + "';";
        logger.debug("Selecting client with API key {}", APIKey);
        return createClientModel(sql);
    }

    /**
     * Returns a Client object. The method takes an SQL query as a string as an argument. When called it connects to the database,
     * instantiates the Client object, sets it's ID and then returns the objectg.
     * @param sql an SQL query as a String
     * @return    a Client object
     * @see       Client
     */
    private Client createClientModel(String sql){
        try (Connection conn = connection.connect();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql)){
            if (rs.next()){
                Client client = new Client(rs.getString("name"), rs.getString("email"), rs.getString("api_key"));
                client.setId(rs.getInt("id"));
                return client;
            }
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
