package com.cst2335.tran0450;
public class Message {
    private String message;
    private int type;

    Message(String message,int type) {
        this.message = message;
        this.type = type;
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
}