package com.hawthorn.instagram.Activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hawthorn.instagram.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class YouActivityFragment extends Fragment {


    public YouActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.my_recycler_view);
        ActivityAdapter1 activityAdapter = new ActivityAdapter1() ;
        recyclerView.setAdapter(activityAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return view;

    }

}
