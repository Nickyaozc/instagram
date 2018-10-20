package com.hawthorn.instagram.models;

public class Act {
    private String initializer;
    private String receiver;
    private String content;

    public Act(){}
    public Act(String initializer, String receiver, String content){
        this.initializer = initializer;
        this.receiver = receiver;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getInitializer() {
        return initializer;
    }

    public void setInitializer(String initializer) {
        this.initializer = initializer;
    }

    @Override
    public String toString() {
        return "ActivityModel{" +
                "initializer='" + initializer + '\'' +
                ", receiver='" + receiver + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
