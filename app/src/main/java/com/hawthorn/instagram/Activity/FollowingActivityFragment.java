package com.hawthorn.instagram.Activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.hawthorn.instagram.Models.Following;
import com.hawthorn.instagram.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FollowingActivityFragment extends Fragment {


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
    private ArrayList<String> following;
    public FollowingActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        this.currentUser = mAuth.getCurrentUser();
        photoList1 = new ArrayList<>();
        contentList = new ArrayList<>();
        photoList2 = new ArrayList<>();
        following = new ArrayList<>();
        view = inflater.inflate(R.layout.fragment_activity, container, false);
        // Query database.
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("following")
                .child(currentUser.getUid());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                following.clear();
                for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: following:" + singleSnapshot.getValue(Following.class));
                    following.add(singleSnapshot.getValue(Following.class).getUserid());
                }
                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
                Query query2 = reference2.child("act");
//        Log.d(TAG, "query:" + query);

                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                            Log.d(TAG, "onDataChange: found activity:" + singleSnapshot.getValue(Act.class));
                                if(following.contains((String)singleSnapshot.getValue(Act.class).getInitializer().toString())){
                            Log.d(TAG, "onDataChange: contains:" + singleSnapshot.getValue(Act.class).getInitializer());
                            if(contentList != null && singleSnapshot.getValue(Act.class).getContent() != null){
                                Log.d(TAG, "onDataChange: adding:" + singleSnapshot.getValue(Act.class).toString());
                                photoList1.add(singleSnapshot.getValue(Act.class).getPhoto1());
                                contentList.add(singleSnapshot.getValue(Act.class).getContent());
                                photoList2.add(singleSnapshot.getValue(Act.class).getPhoto2());
                            }
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        // Prepare data set.



        return view;

    }

}
