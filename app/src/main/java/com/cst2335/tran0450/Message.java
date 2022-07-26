package com.cst2335.tran0450;
public class Message {
    private String message;
    private int type;
    private long id;

    Message(String message,int type, long id) {
        this.message = message;
        this.type = type;
        this.id = id;

    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public long getId() {
        return id;
    }
}