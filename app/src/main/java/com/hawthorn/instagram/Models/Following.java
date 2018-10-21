package com.hawthorn.instagram.Models;

public class Following {
    private String userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    public Following(){}
    public Following(String userid){
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Following{" +
                "userid='" + userid + '\'' +
                '}';
    }
}
