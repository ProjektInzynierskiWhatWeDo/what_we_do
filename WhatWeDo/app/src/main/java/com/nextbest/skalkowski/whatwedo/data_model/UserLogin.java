package com.nextbest.skalkowski.whatwedo.data_model;

public class UserLogin {
    private String email;
    private String password;
    private String google;
    private String facebook;

    public UserLogin(String email, String password, String google, String facebook) {
        this.email = email;
        this.password = password;
        this.google = google;
        this.facebook = facebook;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGoogle() {
        return google;
    }

    public void setGoogle(String google) {
        this.google = google;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }
}
