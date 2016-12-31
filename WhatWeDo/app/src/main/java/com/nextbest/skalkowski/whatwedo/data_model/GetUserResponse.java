package com.nextbest.skalkowski.whatwedo.data_model;

import com.nextbest.skalkowski.whatwedo.local_database.UserPreference;

import java.util.List;

public class GetUserResponse {
    private int id;
    private String name;
    private String email;
    private String user_image;
    private List<UserPreference> userPreferences;


    public GetUserResponse() {
    }

    public GetUserResponse(int id, String name, String email, String user_image, List<UserPreference> userPreferences) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.user_image = user_image;
        this.userPreferences = userPreferences;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<UserPreference> getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(List<UserPreference> userPreferences) {
        this.userPreferences = userPreferences;
    }
}
