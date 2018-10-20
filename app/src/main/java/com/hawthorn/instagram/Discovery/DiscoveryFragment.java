package com.hawthorn.instagram.Discovery;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.hawthorn.instagram.Profile.ProfileActivity;
import com.hawthorn.instagram.R;
import com.hawthorn.instagram.Utils.UserListAdapter;
import com.hawthorn.instagram.models.RecommendedUsers;
import com.hawthorn.instagram.models.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseUser;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.support.v4.content.ContextCompat.getSystemService;


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
    private List<Users> mUserList;
    private UserListAdapter mAdapter;
    private UserListAdapter rAdapter;///zhe
    private List<Users> rUserList;///zhe
    private boolean searching = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_discovery, container, false);
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

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //searching = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = mSearchParam.getText().toString().toLowerCase(Locale.getDefault());
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
                        Log.d(TAG, "onDataChange: found user:" + singleSnapshot.getValue(Users.class).toString());

                        mUserList.add(singleSnapshot.getValue(Users.class));
                        Log.d(TAG,"SEARCH FRIENDS OBJECT:" + singleSnapshot.getValue(Users.class));
                        //update the users list view
                        updateUsersList();

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void updateUsersList(){
        Log.d(TAG, "updateUsersList: updating users list");

        mAdapter = new UserListAdapter(getContext(), R.layout.layout_user_listitem, mUserList);
        rAdapter = new UserListAdapter(getContext(), R.layout.layout_user_listitem, rUserList);///zhe

        mListView.setAdapter(mAdapter);
        mListView.setAdapter(rAdapter);///zhe

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Log.d(TAG, "onItemClick: selected user: " + mUserList.get(position).toString());

                //navigate to profile activity


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
                    for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                        Log.d(TAG, "onDataChange: randomsuggestion:" + singleSnapshot.getValue(Users.class).toString());

                        rUserList.add(singleSnapshot.getValue(Users.class));
                        Log.d(TAG,"random suggestion OBJECT:" + singleSnapshot.getValue(Users.class));
                        updateUsersList();///zhe



                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


    }






}
