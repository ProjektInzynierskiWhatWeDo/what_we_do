package com.nextbest.skalkowski.whatwedo.local_database;

import com.orm.SugarRecord;

public class GroupMemberImage extends SugarRecord {
    private String user_image;

    public GroupMemberImage(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public GroupMemberImage() {
    }
}
