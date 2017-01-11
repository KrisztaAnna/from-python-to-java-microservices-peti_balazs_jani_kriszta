package com.codecool.reviewservice.errorHandling;

import java.sql.SQLException;

/**
 * This class extends the SQLException and it is for handling when an invalid API Key is provided.
 *
 * @author Kriszta
 */
public class InvalidClient extends SQLException {
    /**
     * An exception that provides information on an APIKey error.
     * @param s The message you want to display.
     */
    public InvalidClient(String s){
        super(s);
    }
}

