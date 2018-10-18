package com.hawthorn.instagram.Photo;


import android.app.Activity;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

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
    private String mSelectedImagePath;

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
        final int imageWidth = gridWidth/NUM_GRID_COLUMNS;
        mGridView.setColumnWidth(imageWidth);

        //load photo to gridView
        if (imgURLs != null) {
            GridImageAdapter adapter = new GridImageAdapter(getActivity(), R.layout.layout_grid_imageview, mAppend, imgURLs);
            mGridView.setAdapter(adapter);
            setmGalleryImageView(imgURLs.get(0), mGalleryImageView, mAppend);
            mSelectedImagePath = imgURLs.get(0);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d(TAG, "onItemClick: selected an image: " + imgURLs.get(i));
                    setmGalleryImageView(imgURLs.get(i), mGalleryImageView, mAppend);
                    mSelectedImagePath = imgURLs.get(i);
                }
            });
        } else {
            Toast.makeText(getActivity(), "No photo in " + selectedDir, Toast.LENGTH_SHORT).show();
        }
    }

    private void setmGalleryImageView(String imgURL, ImageView imageView, String append) {
        Log.d(TAG, "setmGalleryImageView: setting image to GalleryImageView");
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
        imageLoader.displayImage(append + imgURL, imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

}
