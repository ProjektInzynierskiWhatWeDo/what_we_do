package com.nextbest.skalkowski.whatwedo.local_database;

import android.widget.Toast;

import com.orm.SugarRecord;

public class Token extends SugarRecord {
    private String token;
    private int loggedBy;

    public Token() {
    }

    public Token(String token, int loggedBy) {
        this.token = token;
        this.loggedBy = loggedBy;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getLoggedBy() {
        return loggedBy;
    }

    public void setLoggedBy(int loggedBy) {
        this.loggedBy = loggedBy;
    }

    public static boolean isToken() {
        if (Token.count(Token.class) == 0) {
            return false;
        }
        return true;
    }
    public static String getUserToken(){
        if(isToken()){
            return Token.listAll(Token.class).get(0).getToken();
        }
        return null;
    }

}

