package com.hawthorn.instagram;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;

public class PhotoActivity extends AppCompatActivity {

    Uri imageUri;
    int CAPTURE_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        capturePhoto();

    }

    private void capturePhoto() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(cameraIntent);
        String imageName = "image.jpg";
        imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), imageName));
        startActivityForResult(cameraIntent, CAPTURE_IMAGE);
    }
}
