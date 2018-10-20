package com.hawthorn.instagram.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hawthorn.instagram.Models.User;
import com.hawthorn.instagram.Models.UserAccountSettings;
import com.hawthorn.instagram.Models.UserSettings;
import com.hawthorn.instagram.R;

public class FirebaseMethods {

    private static final String TAG = "FirebaseMethods";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String userID;

    private Context mContext;

    public FirebaseMethods(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mContext = context;

        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
//        userID = "9bFKMMoAqcdAgGHCsndGU1yM3rj2";
    }

    public UserSettings getUserSettings(DataSnapshot dataSnapshot){
        Log.d(TAG, "getUserAccountSettings: retrieving user account settings from firebase.");
        UserAccountSettings settings  = new UserAccountSettings();
        User user = new User();

        for(DataSnapshot ds: dataSnapshot.getChildren()){

            // user_account_settings node
            if(ds.getKey().equals(mContext.getString(R.string.dbname_user_account_settings))){
                Log.d(TAG, "getUserAccountSettings: datasnapshot: " + ds);

                try{

                    settings.setDisplay_name(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getDisplay_name()
                    );
                    settings.setUsername(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getUsername()
                    );
                    settings.setWebsite(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getWebsite()
                    );
                    settings.setDescription(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getDescription()
                    );
                    settings.setProfile_photo(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getProfile_photo()
                    );
                    settings.setPosts(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getPosts()
                    );
                    settings.setFollowing(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getFollowing()
                    );
                    settings.setFollowers(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getFollowers()
                    );

                    Log.d(TAG, "getUserAccountSettings: retrieved user_account_settings information: " + settings.toString());
                }catch (NullPointerException e){
                    Log.e(TAG, "getUserAccountSettings: NullPointerException: " + e.getMessage() );
                }
            }
//            // users node
//            if(ds.getKey().equals(mContext.getString(R.string.dbname_users))) {
//                Log.d(TAG, "getUserAccountSettings: datasnapshot: " + ds);
//
//                user.setUsername(
//                        ds.child(userID)
//                                .getValue(User.class)
//                                .getUsername()
//                );
//                user.setEmail(
//                        ds.child(userID)
//                                .getValue(User.class)
//                                .getEmail()
//                );
//                user.setPhone_number(
//                        ds.child(userID)
//                                .getValue(User.class)
//                                .getPhone_number()
//                );
//                user.setUser_id(
//                        ds.child(userID)
//                                .getValue(User.class)
//                                .getUser_id()
//                );
//
//                Log.d(TAG, "getUserAccountSettings: retrieved users information: " + user.toString());
//            }
        }
        return new UserSettings(user, settings);

    }

}


























