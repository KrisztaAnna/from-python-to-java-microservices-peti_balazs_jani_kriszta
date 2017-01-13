package com.codecool.reviewservice.controller;

import com.codecool.reviewservice.dao.ClientDao;
import com.codecool.reviewservice.dao.implementation.ClientDaoJdbc;
import com.codecool.reviewservice.email.Email;
import com.codecool.reviewservice.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 * Class for controlling the registration of new Clients.
 */
public class ClientController {
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    private static ClientDao clientDao = ClientDaoJdbc.getInstance();


    /**
     * This method is triggered by the "/newClient" route in the Server. It instantiates a
     * new Client object, saves it into the database and sends an email to the new client
     * which contains the client's registration data and the API Key which they will need later
     * for using the API service.
     * At the end it redirects to the "/success" route.
     * @param request   a Spark request object
     * @param response  a Spark response object
     * @return          In a future verion this method will render a new view, at the moment it simply redirects to the following
     *                  route: "/success"
     * @see             ClientDao
     * @see             Logger
     */
    // TODO: 2017.01.04. integrate email sending service
    public static ModelAndView newClient(Request request, Response response) {
        Client newClient = new Client(request.queryParams("name"), request.queryParams("email"));
        logger.info("New client created: {}", newClient.toString());
        clientDao.add(newClient);
        Email.newClientEmail(newClient);
        response.redirect("/success");
        return null;
    }
}
