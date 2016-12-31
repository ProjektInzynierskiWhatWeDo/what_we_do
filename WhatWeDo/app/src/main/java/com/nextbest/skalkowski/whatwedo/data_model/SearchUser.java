package com.nextbest.skalkowski.whatwedo.data_model;


public class SearchUser {
    private int groupId;
    private String name;

    public SearchUser(int groupId, String name) {
        this.groupId = groupId;
        this.name = name;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
