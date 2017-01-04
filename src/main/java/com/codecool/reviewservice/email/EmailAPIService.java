package com.codecool.reviewservice.email;

import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.net.URI;

/**
 * Created by krisztinabaranyai on 03/01/2017.
 */
public class EmailAPIService {
    private static final String EMAIL_API_URL = "...";
    private static EmailAPIService INSTANCE;


    private String execute(URI uri) throws IOException {
        return Request.Get(uri)
                .execute()
                .returnContent()
                .asString();
    }
}
