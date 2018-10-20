package com.hawthorn.instagram.Activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hawthorn.instagram.R;
import com.hawthorn.instagram.Utils.Firebasemethods;
import com.hawthorn.instagram.models.Act;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class YouActivityFragment extends Fragment {
    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private Firebasemethods mFirebaseMethods;
    private String TAG = "YOU ACTIVITY";
    private ArrayList mActivityList;
    private RecyclerView recyclerView;
    public YouActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        mActivityList = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        recyclerView = view.findViewById(R.id.my_recycler_view);
        ActivityAdapter activityAdapter = new ActivityAdapter(mActivityList) ;
        recyclerView.setAdapter(activityAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        // Query database.
        setupFirebaseAuth();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("act").orderByChild("receiver").equalTo("3xUU");
//        Log.d(TAG, "query:" + query);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
//                    Log.d(TAG, "onDataChange: found activity:" + singleSnapshot.getValue(Act.class).toString());
                    mActivityList.add(singleSnapshot.getValue(Act.class).getContent());
                    ActivityAdapter activityAdapter = new ActivityAdapter(mActivityList) ;
                    recyclerView.setAdapter(activityAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    //update the users list view
//                    updateUsersList();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // Prepare data set.



        return view;

    }

    /*
    ---------------------------------------- Firebase ---------------------------------------------
     */

    /**
     * set up Firebase auth object
     */
    private void setupFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Checuk if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }


}
