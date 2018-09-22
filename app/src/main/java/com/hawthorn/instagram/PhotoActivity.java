package com.hawthorn.instagram;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoActivity extends AppCompatActivity {

    Uri imageUri;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);


        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        /*
        Ensure that there's a camera activity to handle the intent:
        the startActivityForResult() method is protected by a condition
        that calls resolveActivity(), which returns the first activity component
        that can handle the intent.
        */
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
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
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        System.out.println("@@@@@@@@@@@" + storageDir);
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
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            //don't compare the data to null, it will always come as  null because we are providing a file URI, so load with the imageFilePath we obtained before opening the cameraIntent
            ImageView imageView = (ImageView) findViewById(R.id.myImageView);
            Glide.with(this).load(mCurrentPhotoPath).into(imageView);
            // If you are using Glide.
        }
    }

    private boolean checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(PhotoActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            return false;
        }else{
            return true;
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(PhotoActivity.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(PhotoActivity.this,
                    "Write External Storage permission allows us to do store images. "
                            + "Please allow this permission in App Settings.",
                    Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(PhotoActivity.this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
//            Log.v("value","Permission: "+permissions[0]+ " was "+grantResults[0]);
//            //resume tasks needing this permission
//        }
//    }
}
