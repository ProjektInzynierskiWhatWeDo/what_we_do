package com.nextbest.skalkowski.whatwedo.data_model;


public class ResponseToInvite {
    private int group_id;
    private int status;

    public ResponseToInvite(int group_id, int status) {
        this.group_id = group_id;
        this.status = status;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
