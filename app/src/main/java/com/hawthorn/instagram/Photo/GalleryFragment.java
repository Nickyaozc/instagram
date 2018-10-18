package com.hawthorn.instagram.Photo;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.hawthorn.instagram.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {
    static final String TAG = "GalleryFragment";

    //widget
    private GridView mGridView;
    private ImageView mGalleryImageView;
    private ProgressBar mProgressBar;
    //var



    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        mGalleryImageView = (ImageView) view.findViewById(R.id.galleryImageView);
        mGridView = (GridView) view.findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(view.GONE);
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: started");
        return view;
    }

}
