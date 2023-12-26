package com.example.tobeytravelplanner.objects;

import java.util.ArrayList;
import java.util.List;

public class Message {
    private String author;
    private String messageContent;
    private String itenaryId;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getItenaryId() {
        return itenaryId;
    }

    public void setItenaryId(String itenaryId) {
        this.itenaryId = itenaryId;
    }
}
