package com.nextbest.skalkowski.whatwedo.local_database;

import com.orm.SugarRecord;

public class LoggedUser extends SugarRecord {
    private int idUser;
    private String name;
    private String email;
    private String image;

    public LoggedUser() {
    }

    public LoggedUser(int idUser, String name, String email, String image) {
        this.idUser = idUser;
        this.name = name;
        this.email = email;
        this.image = image;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static boolean isUser() {
        if (LoggedUser.count(LoggedUser.class) == 0) {
            return false;
        }
        return true;
    }

    public static LoggedUser getLoggedUser() {
        if (isUser()) {
            return LoggedUser.listAll(LoggedUser.class).get(0);
        }
        return null;
    }

    public static void deleteLoggedUser() {
        LoggedUser.deleteAll(LoggedUser.class);
    }

    public void userLogout(){
        //todo dopisać usuwanie z kolejnych tabel
        LoggedUser.deleteAll(LoggedUser.class);
        Token.deleteAll(Token.class);
        UserPreference.deleteAll(UserPreference.class);
    }

}
