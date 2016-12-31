package com.nextbest.skalkowski.whatwedo.data_model;


public class SendMessage {
    private int id;
    private int local_id;
    private String message;

    public SendMessage(int id, int local_id, String message) {
        this.id = id;
        this.local_id = local_id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(int local_id) {
        this.local_id = local_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
