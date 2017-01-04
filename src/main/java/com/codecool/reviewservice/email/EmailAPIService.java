package com.codecool.reviewservice.email;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import spark.utils.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by krisztinabaranyai on 03/01/2017.
 */
public class EmailAPIService {
    private static final String EMAIL_API_URL = "...";
    private static EmailAPIService INSTANCE;


    public static String sendReviewForModerating(String email) throws URISyntaxException, IOException{
        URIBuilder builder = new URIBuilder(EMAIL_API_URL);

        if (!StringUtils.isEmpty(email)) {
            builder.addParameter("email", email);
        }
        return execute(builder.build());
    }

    private static String execute(URI uri) throws IOException {
        return Request.Get(uri)
                .execute()
                .returnContent()
                .asString();
    }
}
