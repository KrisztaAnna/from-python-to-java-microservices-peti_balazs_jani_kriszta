package com.codecool.reviewservice.controller;

import spark.ModelAndView;
import java.util.HashMap;
import spark.Request;
import spark.Response;
import java.util.Map;


public class RegistrationPageController {
    public static ModelAndView renderRegistrationPage(Request request, Response response) {
        Map params = new HashMap<>();
        return new ModelAndView(params, "product/registration");
    }
}


