package com.nextbest.skalkowski.whatwedo.local_database;

import com.orm.SugarRecord;


public class UserSettings extends SugarRecord {
    private boolean notification;
    private boolean sound;
    private boolean vibrate;
    private int distance;

    public UserSettings() {
    }

    public UserSettings(boolean notification, boolean sound, boolean vibrate, int distance) {
        this.notification = notification;
        this.sound = sound;
        this.vibrate = vibrate;
        this.distance = distance;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public boolean isSound() {
        return sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public static UserSettings getUserSettings(){
        UserSettings userSettings;
        if(UserSettings.count(UserSettings.class) == 0){
             userSettings = new UserSettings(true,true,true,50);
            userSettings.save();
        }else {
            userSettings = listAll(UserSettings.class).get(0);
        }
        return userSettings;
    }
}
