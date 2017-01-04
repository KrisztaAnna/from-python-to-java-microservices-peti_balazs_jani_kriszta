package com.codecool.reviewservice.controller;

import com.codecool.reviewservice.dao.implementation.ClientDaoJdbc;
import com.codecool.reviewservice.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

public class RegistrationPageController {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationPageController.class);
    private static final ClientDaoJdbc clientDao = ClientDaoJdbc.getInstance();

    public static void createNewClient(Request request, Response response) {
        String companyName = request.params("name");
        String email = request.params("email");
        Client newClient = new Client(companyName, email);
        clientDao.add(newClient);
        logger.debug("Save new client: " + newClient.toString());
    }
}
