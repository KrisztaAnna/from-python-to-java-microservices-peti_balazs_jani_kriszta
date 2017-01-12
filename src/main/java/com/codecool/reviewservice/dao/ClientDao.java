package com.codecool.reviewservice.dao;

import com.codecool.reviewservice.model.Client;

/**
 * ClientDao is an interface for providing some specific data operations on the Client model.
 * Find further comments on the methods in the ClientDaoJdbc class.
 */
public interface ClientDao {
    void add(Client clientModel);
    void remove(String APIKey);
    Client getById(int id);
    Client getByAPIKey(String APIKey);
}
