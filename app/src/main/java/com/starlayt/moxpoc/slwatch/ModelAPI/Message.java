package com.starlayt.moxpoc.slwatch.ModelAPI;

public class Message {

    protected int id;
    protected String message;
    protected String senderName;

    public Message(int id, String message, String senderName) {
        this.id = id;
        this.message = message;
        this.senderName = senderName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
