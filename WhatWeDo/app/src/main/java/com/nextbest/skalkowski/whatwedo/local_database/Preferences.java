package com.nextbest.skalkowski.whatwedo.local_database;

import com.nextbest.skalkowski.whatwedo.R;

import java.util.HashMap;
import java.util.Map;

public class Preferences {
    private int id;
    private int color;

    public Preferences(int id, int color) {
        this.id = id;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    static Map<Integer, Preferences> allPreferences = new HashMap<>();

    static {
        if (allPreferences.isEmpty()) {
            allPreferences.put(1, new Preferences(1, R.color.preferences1));
            allPreferences.put(2, new Preferences(2, R.color.preferences2));
            allPreferences.put(3, new Preferences(3, R.color.preferences3));
            allPreferences.put(4, new Preferences(4, R.color.preferences4));
            allPreferences.put(5, new Preferences(5, R.color.preferences5));
            allPreferences.put(6, new Preferences(6, R.color.preferences6));
            allPreferences.put(7, new Preferences(7, R.color.preferences7));
            allPreferences.put(8, new Preferences(8, R.color.preferences8));
            allPreferences.put(9, new Preferences(9, R.color.preferences9));
            allPreferences.put(10, new Preferences(10, R.color.preferences10));
            allPreferences.put(11, new Preferences(11, R.color.preferences11));
        }
    }

    public static int getPreferenceColor(int preferenceId) {
        try {
            if (preferenceId == 0){
                return R.color.preferences1;
            }else{
                return allPreferences.get(preferenceId).getColor();
            }

        } catch (NullPointerException e) {
            return R.color.preferences1;
        }
    }
}
