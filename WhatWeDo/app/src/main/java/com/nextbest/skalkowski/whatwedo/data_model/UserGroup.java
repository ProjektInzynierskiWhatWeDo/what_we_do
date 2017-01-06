package com.nextbest.skalkowski.whatwedo.data_model;

import com.nextbest.skalkowski.whatwedo.local_database.GroupMemberImage;

import java.io.Serializable;
import java.util.List;

public class UserGroup implements Serializable{
    private int group_id;
    private int owner_id;
    private String name;
    private int count;
    private List<GroupMemberImage> member_image = null;

    public UserGroup(int group_id, int owner_id, String name, int count, List<GroupMemberImage> member_image) {
        this.group_id = group_id;
        this.owner_id = owner_id;
        this.name = name;
        this.count = count;
        this.member_image = member_image;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<GroupMemberImage> getMember_image() {
        return member_image;
    }

    public void setMember_image(List<GroupMemberImage> member_image) {
        this.member_image = member_image;
    }
}
