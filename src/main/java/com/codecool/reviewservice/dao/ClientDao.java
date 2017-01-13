package com.codecool.reviewservice.dao;

import com.codecool.reviewservice.model.Client;

/**
 * ClientDao is an interface which provides specific data operations on the Client model.
 * Find further comments on its methods in its in implementation in the ClientDaoJdbc class.
 * @author Peti
 */
public interface ClientDao {
    void add(Client clientModel);
    void remove(String APIKey);
    Client getById(int id);
    Client getByAPIKey(String APIKey);
}
