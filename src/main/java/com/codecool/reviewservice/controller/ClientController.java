package com.codecool.reviewservice.controller;

import com.codecool.reviewservice.dao.ClientDao;
import com.codecool.reviewservice.dao.implementation.ClientDaoJdbc;
import com.codecool.reviewservice.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

public class ClientController {
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    private static ClientDao clientDao = ClientDaoJdbc.getInstance();


    // create new client & save it to the database & sending registration email
    // TODO: 2017.01.04. integrate email sending service
    private void newClient(Request request, Response response){
        Client newClient = new Client(request.params("name"), request.params("email"));
        logger.info("New client created: {}", newClient.toString());
        clientDao.add(newClient);
    }
}
