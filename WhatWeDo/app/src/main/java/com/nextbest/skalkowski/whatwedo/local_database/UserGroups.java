package com.nextbest.skalkowski.whatwedo.local_database;

import com.orm.SugarRecord;

import java.io.Serializable;


public class UserGroups extends SugarRecord implements Serializable {
    private int group_id;
    private int owner_id;
    private String name;
    private int status;
    private int count;
    private String first_image;
    private String second_image;
    private String third_image;
    private String fourth_image;

    public UserGroups() {
    }

    public UserGroups(int group_id, int owner_id, String name, int status, int count, String first_image, String second_image, String third_image, String fourth_image) {
        this.group_id = group_id;
        this.owner_id = owner_id;
        this.name = name;
        this.status = status;
        this.count = count;
        this.first_image = first_image;
        this.second_image = second_image;
        this.third_image = third_image;
        this.fourth_image = fourth_image;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getFirst_image() {
        return first_image;
    }

    public void setFirst_image(String first_image) {
        this.first_image = first_image;
    }

    public String getSecond_image() {
        return second_image;
    }

    public void setSecond_image(String second_image) {
        this.second_image = second_image;
    }

    public String getThird_image() {
        return third_image;
    }

    public void setThird_image(String third_image) {
        this.third_image = third_image;
    }

    public String getFourth_image() {
        return fourth_image;
    }

    public void setFourth_image(String fourth_image) {
        this.fourth_image = fourth_image;
    }
}