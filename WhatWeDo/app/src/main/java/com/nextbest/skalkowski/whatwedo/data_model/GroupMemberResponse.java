package com.nextbest.skalkowski.whatwedo.data_model;

import java.util.List;


public class GroupMemberResponse {
    private int status;
    private int group_id;
    private List<Member> message;

    public GroupMemberResponse(int status, int group_id, List<Member> message) {
        this.status = status;
        this.group_id = group_id;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public List<Member> getMessage() {
        return message;
    }

    public void setMessage(List<Member> message) {
        this.message = message;
    }
}
