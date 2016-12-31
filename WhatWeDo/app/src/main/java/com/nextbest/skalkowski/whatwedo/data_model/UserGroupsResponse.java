package com.nextbest.skalkowski.whatwedo.data_model;

import java.util.List;

public class UserGroupsResponse {
    private int status;
    private List<UserGroup> message;

    public UserGroupsResponse(int status, List<UserGroup> message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<UserGroup> getMessage() {
        return message;
    }

    public void setMessage(List<UserGroup> message) {
        this.message = message;
    }
}
