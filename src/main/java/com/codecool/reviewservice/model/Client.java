package com.codecool.reviewservice.model;

import java.util.UUID;

public class Client {
    String name;
    int id;
    String APIKey;
    String email;

    /**
     * his constructor is used when a new client has registered with the Review Service and a client object is being
     * initialized for the first time. This means that the client doesn't have a hash (=APIKey) in the database yet,
     * so this constructor assigns one to it.
     * @param name  Name or Company name of the client.
     * @param email The email address of the client.
     */
    public Client(String name, String email){
        this.name = name;
        this.email = email;
        APIKey = UUID.randomUUID().toString();
    }

    /**
     * This constructor is used when a hash (APIKey) has been already assigned to the client.
     * @param name      Name or Company name of the client.
     * @param email     The email address of the client.
     * @param APIKey    The APIKey of the client.
     */
    public Client(String name, String email, String APIKey){
        this.name = name;
        this.email = email;
        this.APIKey = APIKey;
    }

    /**
     * Sets the ID of the client object.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the client.
     * @return The name of the client as a String.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the id of the client.
     * @return The id of the client as an integer.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the APIKey of the client.
     * @return The APIKey of the client as a String.
     */
    public String getAPIKey(){
        return APIKey;
    }

    /**
     * Returns the email address of the client.
     * @return The email address of the client as a String.
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method converts a Client object into a String.
     * @return The client object as a String.
     */
    @Override
    public String toString(){
        return String.format("" +
                "Company name: %s\n" +
                "Email address: %s\n" +
                "API Key: %s\n", getName(), getEmail(), getAPIKey());
    }
}
