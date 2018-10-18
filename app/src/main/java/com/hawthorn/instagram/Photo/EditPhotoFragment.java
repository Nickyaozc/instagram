package com.hawthorn.instagram.Photo;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hawthorn.instagram.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.hawthorn.instagram.Photo.PhotoActivity.REQUEST_IMAGE_CAPTURE;


/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends Fragment {
    //const
    static final String TAG = "CameraFragment";

    //var
    String mCurrentPhotoPath;

    //widgets
    private Button startCameraBtn;
    private ImageView imageView;


    public CameraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        startCameraBtn = (Button) view.findViewById(R.id.startCameraBtn);
        imageView = (ImageView) view.findViewById(R.id.takenPhotoImageView);
        Log.d(TAG, "onCreateView: started");
        setButtonListener(startCameraBtn);
        return view;
    }

    private void setButtonListener(Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.hawthorn.instagram.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",     /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            Log.d(TAG, "onActivityResult: started");
            Glide.with(this).load(mCurrentPhotoPath).into(imageView);
        }
    }
//
//    private boolean checkPermission() {
//        int permissionCheck = ContextCompat.checkSelfPermission(PhotoActivity.this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            return false;
//        }else{
//            return true;
//        }
//    }
//
//    private void requestPermission() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(PhotoActivity.this,
//                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            Toast.makeText(PhotoActivity.this,
//                    "Write External Storage permission allows us to do store images. "
//                            + "Please allow this permission in App Settings.",
//                    Toast.LENGTH_LONG).show();
//        } else {
//            ActivityCompat.requestPermissions(PhotoActivity.this,
//                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
//            Log.v("value","Permission: "+permissions[0]+ " was "+grantResults[0]);
//            //resume tasks needing this permission
//        }
//    }
}
