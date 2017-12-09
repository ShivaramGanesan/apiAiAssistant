package com.example.sanjaye.assistant;

/**
 * Created by SANJAYE on 07-12-2017.
 */

public class ChatMessage {

    String content;
    String time;
    boolean isMine;

    public ChatMessage() {
    }

    public ChatMessage(String content, String time,boolean isMine) {
        this.content = content;
        this.time = time;
        this.isMine = isMine;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }
}
