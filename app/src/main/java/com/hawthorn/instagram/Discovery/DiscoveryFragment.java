package com.hawthorn.instagram.Discovery;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hawthorn.instagram.Models.Act;
import com.hawthorn.instagram.Models.User;
import com.hawthorn.instagram.Models.UserAccountSettings;
import com.hawthorn.instagram.R;
import com.hawthorn.instagram.Utils.UserListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoveryFragment extends Fragment {
    private static final String TAG = "discoveryfragment";
    private static final int ACTIVITY_NUM = 1;


    private Context mContext = getContext();

    //widgets
    private EditText mSearchParam;
    private ListView mListView;


    public DiscoveryFragment() {
        // Required empty public constructor
    }
    //vars
    private List<User> mUserList;
    private UserListAdapter mAdapter;
    private UserListAdapter rAdapter;///zhe
    private List<User> rUserList;///zhe
    private boolean searching = false;
    private String text;
    private long currentFollowing;
    private long currentFollowers;
    private User mUser;
    private String init_name;
    private String photo1;
    private String photo2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_discovery, container, false);

        Query query1 = FirebaseDatabase.getInstance().getReference()
                .child("user_account_settings")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                photo1 = dataSnapshot.getValue(UserAccountSettings.class).getProfile_photo();
                init_name = dataSnapshot.getValue(UserAccountSettings.class).getUser_name();
                Log.d(TAG, "onCreateView "+photo1);
                Log.d(TAG, "onCreateView "+init_name);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        mSearchParam = view.findViewById(R.id.search);
        mListView = view.findViewById(R.id.listView);
        rUserList = new ArrayList<>();
       // if(!searching) randomsuggestion();
        randomsuggestion();
        initTextListener();//



        return view;
    }


    private void initTextListener(){
        Log.d(TAG, "initTextListener: initializing");

        mUserList = new ArrayList<>();


        mSearchParam.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                randomsuggestion();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //searching = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

                text = mSearchParam.getText().toString().toLowerCase(Locale.getDefault());
                searchForMatch(text);
                //searching = true;
            }
        });
    }

    private void searchForMatch(String keyword){
        Log.d(TAG, "searchForMatch: searching for a match: " + keyword);
        mUserList.clear();
        //update the users list view
        if(keyword.length() ==0){

        }else{
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            Query query = reference.child(getString(R.string.dbname_users))
                    .orderByChild(getString(R.string.field_username)).equalTo(keyword);
            Log.d(TAG,"PRINT query "+query);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                        Log.d(TAG, "onDataChange: found user:" + singleSnapshot.getValue(User.class).toString());

                        mUserList.add(singleSnapshot.getValue(User.class));
                        Log.d(TAG,"SEARCH FRIENDS OBJECT:" + singleSnapshot.getValue(User.class));
                        //update the users list view
                        updateUserList();

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void updateUserList(){
        Log.d(TAG, "updateUserList: updating users list");

        mAdapter = new UserListAdapter(getContext(), R.layout.layout_user_listitem, mUserList);
        rAdapter = new UserListAdapter(getContext(), R.layout.layout_user_listitem, rUserList);///zhe
        if(text != null && text.length()>0) mListView.setAdapter(mAdapter);
        else mListView.setAdapter(rAdapter);///zhe

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Log.d(TAG, "onItemClick: selected user: " + mUserList.get(position).toString());
                mUser = (User)parent.getAdapter().getItem(position);
                Toast.makeText(getActivity(), "Followed user "+mUser.getUsername()+" successfully",
                        Toast.LENGTH_SHORT).show();
                followUser();
                addAct();


            }
        });
    }

    private void randomsuggestion(){
        Log.d(TAG, "randomsuggestion");
        //update the users list view
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
           // Query query = reference.child(getString(R.string.dbname_users));
            //Log.d(TAG,"PRINT query "+query);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    rUserList.clear();
                    for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                        Log.d(TAG, "onDataChange: randomsuggestion:" + singleSnapshot.getValue(User.class).toString());

                        rUserList.add(singleSnapshot.getValue(User.class));
                        Log.d(TAG,"random suggestion OBJECT:" + singleSnapshot.getValue(User.class));
                        updateUserList();///zhe



                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


    }
    private void followUser(){
        // update following
        FirebaseDatabase.getInstance().getReference()
                .child("following")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mUser.getUser_id())
                .child(getString(R.string.field_user_id))
                .setValue(mUser.getUser_id());
        // update follower
        FirebaseDatabase.getInstance().getReference()
                .child("followers")
                .child(mUser.getUser_id())
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(getString(R.string.field_user_id))
                .setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
        // update user_account_settings for following
        Query query1 = FirebaseDatabase.getInstance().getReference()
                .child("user_account_settings")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "follower account: "+dataSnapshot.getValue(UserAccountSettings.class).toString());
                currentFollowing = dataSnapshot.getValue(UserAccountSettings.class).getFollowing();
                photo1 = dataSnapshot.getValue(UserAccountSettings.class).getProfile_photo();
                init_name = dataSnapshot.getValue(UserAccountSettings.class).getUser_name();

                Log.e(TAG, "photo1: " + photo1);
                Log.e(TAG, "in followUser: "+ init_name);
                FirebaseDatabase.getInstance().getReference()
                        .child("user_account_settings")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("following")
                        .setValue(currentFollowing+1);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        // update user_account_settings for followers
        Query query2 = FirebaseDatabase.getInstance().getReference()
                .child("user_account_settings")
                .child(mUser.getUser_id());
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "followee account: "+dataSnapshot.getValue(UserAccountSettings.class).toString());
                photo2 = dataSnapshot.getValue(UserAccountSettings.class).getProfile_photo();
                Log.e(TAG, "photo2: " + photo2);
                currentFollowers = dataSnapshot.getValue(UserAccountSettings.class).getFollowers();
                FirebaseDatabase.getInstance().getReference()
                        .child("user_account_settings")
                        .child(mUser.getUser_id())
                        .child("followers")
                        .setValue(currentFollowers+1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void addAct(){





        Query query1 = FirebaseDatabase.getInstance().getReference()
                .child("user_account_settings")
                .child(mUser.getUser_id());
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "follower account: "+dataSnapshot.getValue(UserAccountSettings.class).toString());
                photo2 = dataSnapshot.getValue(UserAccountSettings.class).getProfile_photo();
                String initializer = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String receiver = mUser.getUser_id();
                String rece_name = mUser.getUsername();
                String content = init_name+" starts following "+rece_name;
                Log.d(TAG, "addAct "+photo1);
                Log.d(TAG, "addAct "+init_name);
                Act act = new Act(initializer, receiver, content, photo1, photo2 );
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                        .child("act");
                String newKey = ref.push().getKey();
                Log.d(TAG, "trying to add act: "+act.toString());
                FirebaseDatabase.getInstance().getReference()
                        .child("act")
                        .child(newKey)
                        .setValue(act);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }





}
