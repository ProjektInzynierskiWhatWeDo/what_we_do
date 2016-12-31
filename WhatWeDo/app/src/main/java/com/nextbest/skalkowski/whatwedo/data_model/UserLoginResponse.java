package com.nextbest.skalkowski.whatwedo.data_model;


public class UserLoginResponse {
    private int status;
    private String message;
    private Boolean register;

    public UserLoginResponse(int status, String message, Boolean register) {
        this.status = status;
        this.message = message;
        this.register = register;
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

    public Boolean getRegister() {
        return register;
    }

    public void setRegister(Boolean register) {
        this.register = register;
    }
}
