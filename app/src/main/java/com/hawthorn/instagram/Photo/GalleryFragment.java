package com.hawthorn.instagram.Photo;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hawthorn.instagram.R;
import com.hawthorn.instagram.Utils.FilePaths;
import com.hawthorn.instagram.Utils.FileSearch;
import com.hawthorn.instagram.Utils.GridImageAdapter;
import com.yashoid.instacropper.InstaCropperView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {
    static final String TAG = "GalleryFragment";

    //widget
    private GridView mGridView;
//    private ImageView mGalleryImageView;
    private InstaCropperView mInstaCropperView;
    private ProgressBar mProgressBar;

    //var, const
    private ArrayList<String> directorires;
    private static final int NUM_GRID_COLUMNS = 4;
    private static final String mAppend = "file:/";
    private String mSelectedImagePath;
    private Uri imageUri;

    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
//        mGalleryImageView = (ImageView) view.findViewById(R.id.galleryImageView);
        mInstaCropperView = (InstaCropperView) view.findViewById(R.id.instacropper);
        mInstaCropperView.setRatios(1, 1, 1);
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
//            setmGalleryImageView(imgURLs.get(0), mGalleryImageView, mAppend);
            imageUri = Uri.fromFile(new File(imgURLs.get(0)));
            Log.d(TAG, "setupGridView: load " + imageUri + " to cropper");
            mInstaCropperView.setImageUri(imageUri);
            mSelectedImagePath = imgURLs.get(0);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d(TAG, "onItemClick: selected an image: " + imgURLs.get(i));
//                    setmGalleryImageView(imgURLs.get(i), mGalleryImageView, mAppend);
                    imageUri = Uri.fromFile(new File(imgURLs.get(i)));
                    Log.d(TAG, "setupGridView: load " + imageUri + " to cropper");
                    mInstaCropperView.setImageUri(imageUri);
                    mSelectedImagePath = imgURLs.get(i);
                }
            });
        } else {
            Toast.makeText(getActivity(), "No photo in " + selectedDir, Toast.LENGTH_SHORT).show();
        }
    }

    public void crop() {
        Log.d(TAG, "crop: crop image");
        mInstaCropperView.crop(
                View.MeasureSpec.makeMeasureSpec(1024, View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                new InstaCropperView.BitmapCallback() {

                    @Override
                    public void onBitmapReady(Bitmap bitmap) {
                        if (bitmap == null) {
                            Toast.makeText(getActivity(), "Returned bitmap is null.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        byte[] cropeedImageBytesArray;
                        Log.d(TAG, "onBitmapReady: obtain cropped bitmap");
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                        cropeedImageBytesArray = os.toByteArray();
                        Intent intent = new Intent(getActivity(), EditPhotoActivity.class);
                        intent.putExtra(getString(R.string.cropped_image), cropeedImageBytesArray);
                        startActivity(intent);
                    }

                }
        );
    }

    private File getFile() {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "instaCropper.jpg");
    }

//    private void setmGalleryImageView(String imgURL, ImageView imageView, String append) {
//        Log.d(TAG, "setmGalleryImageView: setting image to GalleryImageView");
//        ImageLoader imageLoader = ImageLoader.getInstance();
//        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
//        imageLoader.displayImage(append + imgURL, imageView, new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//                mProgressBar.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                mProgressBar.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                mProgressBar.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onLoadingCancelled(String imageUri, View view) {
//                mProgressBar.setVisibility(View.INVISIBLE);
//            }
//        });
//    }

}
