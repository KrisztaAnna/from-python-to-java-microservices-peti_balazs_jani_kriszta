package com.codecool.reviewservice.model;

import java.util.HashMap;

public class Email {
    private String from;
    private String to;
    private String subject;
    private String message;

    public Email(String from, String to, String subject, String message) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public HashMap<String, String> createEmail(){
        HashMap<String, String> email = new HashMap<>();
        email.put("from", from);
        email.put("to", to);
        email.put("subject", subject);
        email.put("message", message);
        return email;
    }

    @Override
    public String toString(){
        return String.format("from: %s, to: %s, subject: %s, message: %s",getFrom(),getTo(),getSubject(),getMessage());
    }

    public static void main(String[] args) {
        Email emailTest = new Email("tolem", "neked", "test", "ez egy teszt");
        System.out.println(emailTest.createEmail());
        System.out.println(emailTest.toString());
    }
}
