package com.nextbest.skalkowski.whatwedo.data_model;


public class GetGroupMember {
    private int group_id;

    public GetGroupMember(int group_id) {
        this.group_id = group_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }
}
