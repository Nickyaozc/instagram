package com.hawthorn.instagram.Photo;


import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.hawthorn.instagram.R;
import com.hawthorn.instagram.Utils.FilePaths;
import com.hawthorn.instagram.Utils.FileSearch;
import com.hawthorn.instagram.Utils.GridImageAdapter;

import java.io.File;
import java.util.ArrayList;

import static android.os.Environment.DIRECTORY_DCIM;


/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {
    static final String TAG = "GalleryFragment";

    //widget
    private GridView mGridView;
    private ImageView mGalleryImageView;
    private ProgressBar mProgressBar;

    //var, const
    private ArrayList<String> directorires;
    private static final int NUM_GRID_COLUMNS = 4;
    private static final String mAppend = "file:/";

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
        directorires = new ArrayList<>();
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: started");
        init();
        return view;
    }

    private void init() {
        // check folders
        FilePaths filePaths = new FilePaths();
        if(FileSearch.getDirectoryPaths(filePaths.PICTURES) != null) {
            directorires = FileSearch.getDirectoryPaths(filePaths.PICTURES);
        }
        directorires.add(filePaths.CAMERA);
        setupGridView(filePaths.CAMERA);
    }

    private void setupGridView(String selectedDir) {
        Log.d(TAG, "setupGridView: directory chosen: " + selectedDir);
        final ArrayList<String> imgURLs = FileSearch.getFilePaths(selectedDir);
        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth/NUM_GRID_COLUMNS;
        mGridView.setColumnWidth(imageWidth);

        //load photo to gridView
        if (imgURLs != null) {
            GridImageAdapter adapter = new GridImageAdapter(getActivity(), R.layout.layout_grid_imageview, mAppend, imgURLs);
            mGridView.setAdapter(adapter);
        } else {
            Toast.makeText(getActivity(), "No photo in " + selectedDir, Toast.LENGTH_SHORT).show();
        }
    }

}
