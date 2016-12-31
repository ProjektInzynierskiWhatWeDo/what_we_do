package com.nextbest.skalkowski.whatwedo.data_model;



public class InviteUser {
    private int user_id;
    private int group_id;

    public InviteUser(int user_id, int group_id) {
        this.user_id = user_id;
        this.group_id = group_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }
}
