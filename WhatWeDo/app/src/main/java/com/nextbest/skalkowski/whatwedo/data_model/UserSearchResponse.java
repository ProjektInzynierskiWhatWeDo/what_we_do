package com.nextbest.skalkowski.whatwedo.data_model;


import java.util.List;

public class UserSearchResponse {
    private int status;
    private List<UserFind> message = null;

    public UserSearchResponse(int status, List<UserFind> message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<UserFind> getMessage() {
        return message;
    }

    public void setMessage(List<UserFind> message) {
        this.message = message;
    }
}
