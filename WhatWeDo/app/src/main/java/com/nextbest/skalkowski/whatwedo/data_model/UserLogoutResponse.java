package com.nextbest.skalkowski.whatwedo.data_model;



public class UserLogoutResponse {
    private int status;
    private String message;

    public UserLogoutResponse() {
    }

    public UserLogoutResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

