package com.nextbest.skalkowski.whatwedo.data_model;

public class SetUserPreference {
    private int preferenceId;
    private int status;

    public SetUserPreference(int preferenceId, int status) {
        this.preferenceId = preferenceId;
        this.status = status;
    }

    public int getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(int preferenceId) {
        this.preferenceId = preferenceId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
