package com.nextbest.skalkowski.whatwedo.data_model;


public class Member {
    private int user_id;
    private String name;
    private String email;
    private String user_image;

    public Member(int user_id, String name, String email, String user_image) {
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.user_image = user_image;
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
}
