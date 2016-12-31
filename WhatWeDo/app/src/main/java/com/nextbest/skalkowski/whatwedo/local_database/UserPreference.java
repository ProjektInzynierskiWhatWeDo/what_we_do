package com.nextbest.skalkowski.whatwedo.local_database;


import com.orm.SugarRecord;

public class UserPreference extends SugarRecord{
    private int preferenceId;
    private String name;
    private Boolean status;

    public UserPreference() {
    }

    public UserPreference(int preferenceId, String name, Boolean status) {
        this.preferenceId = preferenceId;
        this.name = name;
        this.status = status;
    }

    public int getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(int preferenceId) {
        this.preferenceId = preferenceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
