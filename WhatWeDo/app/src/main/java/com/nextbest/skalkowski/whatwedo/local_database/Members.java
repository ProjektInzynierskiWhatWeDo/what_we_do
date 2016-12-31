package com.nextbest.skalkowski.whatwedo.local_database;

import com.nextbest.skalkowski.whatwedo.data_model.Member;
import com.orm.SugarRecord;
import com.orm.util.NamingHelper;


public class Members extends SugarRecord{
    private int user_id;
    private String name;
    private String email;
    private String user_image;
    private int group_id;
    private int event_id;

    public Members() {
    }

    public Members(int user_id, String name, String email, String user_image, int group_id, int event_id) {
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.user_image = user_image;
        this.group_id = group_id;
        this.event_id = event_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public static void deleteByGroupId(int group_id){
       Members.deleteAll(Members.class, NamingHelper.toSQLNameDefault("group_id") + " = ?", String.valueOf(group_id));
    }
}
