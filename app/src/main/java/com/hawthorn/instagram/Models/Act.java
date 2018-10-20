package com.hawthorn.instagram.Models;

public class Act {
    private String initializer;
    private String receiver;
    private String content;
    private String photo1;

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    private String photo2;

    public Act(){}
    public Act(String initializer, String receiver, String content, String photo1, String photo2){
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
