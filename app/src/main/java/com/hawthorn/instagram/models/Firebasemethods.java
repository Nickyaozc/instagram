package com.hawthorn.instagram.models;

import android.content.Intent;
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
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

import android.widget.ListView;
import android.content.Context;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;
import com.hawthorn.instagram.Login.LoginActivity;
import com.hawthorn.instagram.MainActivity;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.support.v7.app.AppCompatActivity;

import com.hawthorn.instagram.R;
///

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hawthorn.instagram.Utils.StringManipulation;


public class Firebasemethods {

    public Context mContext;


    //private FirebaseAuth mAuth;
    public static final String TAG = "FirebaseActivity";

    //private void setupFirebaseAuth() {
        //mAuth = FirebaseAuth.getInstance();
    //}
    //public void Firebasemethods(){
        //setupFirebaseAuth();
    //}
    public FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;
    public FirebaseDatabase mFirebaseDatabase;
    public DatabaseReference myRef;
    public String userID;

    public Firebasemethods(Context context){
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mContext = context;
        if(mAuth.getCurrentUser()!=null){
            userID= mAuth.getCurrentUser().getUid();

        }

    }
    public boolean checkIfUsernameExists(String username, DataSnapshot datasnapshot){
        Log.d(TAG, "checkIfUsernameExists: checking if " + username + " already exists.");

        Users user = new Users();

        for (DataSnapshot ds: datasnapshot.child(userID).getChildren()){
            Log.d(TAG, "checkIfUsernameExists: datasnapshot: " + ds);

            user.setUsername(ds.getValue(Users.class).getUsername());
            Log.d(TAG, "checkIfUsernameExists: username: " + user.getUsername());

            if(StringManipulation.expandUsername(user.getUsername()).equals(username)){
                Log.d(TAG, "checkIfUsernameExists: FOUND A MATCH: " + user.getUsername());
                return true;
            }
        }
        return false;
    }

    public void registerNewEmail(final String email, String password, final String username){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();

                        }
                        else if(task.isSuccessful()){
                            userID = mAuth.getCurrentUser().getUid();
                            Log.d(TAG, "onComplete: Authstate changed: " + userID);
                        }

                    }
                });
    }





    public void addNewUser(String email, String username, String description, String website, String profile_photo) {
        System.out.println(FirebaseDatabase.getInstance());
        //DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        userID= mAuth.getCurrentUser().getUid();

        Users user = new Users(userID,1,email, StringManipulation.condenseUsername(username) );

        myRef.child(mContext.getString(R.string.dbname_users)).child(userID).setValue(user);

        UserAccountSettings settings = new UserAccountSettings(description, username, 0,0,0, profile_photo,username,website );

        myRef.child(mContext.getString(R.string.dbname_user_account_settings)).child(userID).setValue(settings);

    }
    //public static void main(String[]args){

        //addNewUser("tom",0456465553, "tom", "tomding@gmail.com", "tomding", "0", "I am single", "singleguy.com")
    //}

}


    //private UserSettings getUserAccountSettings(DataSnapshot dataSnapshot){
        //Log.d(TAG,"gettingUsersAccountSettings:retrieving user account setting from firebase." );

        //UserAccountSettings settings = new UserAccountSettings();
       // Users user = new Users();
       // for(DataSnapshot ds: dataSnapshot.getChildren()){
           // if(ds.getKey().equals(mContext.getString(R.string.dbname_user_account_settings))){
               // Log.d(TAG, "getUserAccountSettings:datasnapshot:"+ds);{
                   // settings.setDisplay_name(ds.child(userID));

              //  }
           // }
       // }
 //   }














