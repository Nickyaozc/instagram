package com.hawthorn.instagram.models;

public class RecommendedUsers {
    public String username;
    public RecommendedUsers() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public RecommendedUsers( String username) {

        this.username = username;
    }
}
