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
import com.hawthorn.instagram.Models.Act;
import com.hawthorn.instagram.R;

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
    private String TAG = "YOU ACTIVITY";
    private ArrayList<String> photoList1; // For the activity content.
    private ArrayList<String> contentList; // For the activity content.
    private ArrayList<String> photoList2; // For the activity content.
    private RecyclerView recyclerView;
    private FirebaseUser currentUser;
    private View view;
    public YouActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        setupFirebaseAuth();
        this.currentUser = mAuth.getCurrentUser();
        photoList1 = new ArrayList<>();
        contentList = new ArrayList<>();
        photoList2 = new ArrayList<>();
        view = inflater.inflate(R.layout.fragment_activity, container, false);
        // Query database.
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("act").orderByChild("receiver").equalTo(this.currentUser.getUid());
//        Log.d(TAG, "query:" + query);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
//                    Log.d(TAG, "onDataChange: found activity:" + singleSnapshot.getValue(Act.class).toString());
                    if(contentList != null && singleSnapshot.getValue(Act.class).getContent() != null){
                        photoList1.add(singleSnapshot.getValue(Act.class).getPhoto1());
                        contentList.add(singleSnapshot.getValue(Act.class).getContent());
                        photoList2.add(singleSnapshot.getValue(Act.class).getPhoto2());
                    }
                    RecyclerView recyclerView = view.findViewById(R.id.my_recycler_view);
                    ActivityAdapter activityAdapter = new ActivityAdapter(photoList1, contentList, photoList2) ;
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
        this.currentUser = mAuth.getCurrentUser();

    }


}
