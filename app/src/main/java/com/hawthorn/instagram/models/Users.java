package com.hawthorn.instagram.models;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

import android.widget.ListView;
import android.content.Context;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.Query;
import com.hawthorn.instagram.MainActivity;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.support.v7.app.AppCompatActivity;

import com.hawthorn.instagram.R;


public class Users {

    public String user_id;
    public long phone_number;
    public String email;
    public String username;


    //private DatabaseReference mDatabase;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public Users() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Users(  String email,long phone_number, String user_id,String username) {
        this.user_id = user_id;
        this.phone_number = phone_number;
        this.email = email;
        this.username = username;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public long getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(long phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }






}
