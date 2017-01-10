package com.codecool.reviewservice.controller;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is for rendering the registration page
 *
 * @author Kriszta
 */
public class RegistrationPageController {
    /**
     * RegistrationPageController is used to render the registration page of the application for clients.
     * The route "/" calls this method.
     * @param request
     * @param response
     * @return ModalAndView This returns a ModalAndView object which renders registration.html
     */
    public static ModelAndView renderRegistrationPage(Request request, Response response) {
        Map params = new HashMap<>();
        return new ModelAndView(params, "registration");
    }
}


