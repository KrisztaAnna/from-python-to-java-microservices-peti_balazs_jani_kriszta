package com.codecool.reviewservice.errorHandling;

import java.sql.SQLException;

/**
 * Created by krisztinabaranyai on 04/01/2017.
 */
public class InvalidClient extends SQLException {

    public InvalidClient(String s){
        super(s);
    }
}
