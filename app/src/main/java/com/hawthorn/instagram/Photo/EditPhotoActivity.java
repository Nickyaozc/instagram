package com.hawthorn.instagram.Photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.hawthorn.instagram.R;

public class EditPhotoActivity extends AppCompatActivity {
    private final String TAG = "EditPhotoActivity";
    //var
    private byte[] imageByteArray;

    //widgets
    private ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);
        Log.d(TAG, "onCreate: Started");
        mImageView = (ImageView) findViewById(R.id.EditImageView);
        imageByteArray = getIntent().getByteArrayExtra(getString(R.string.cropped_image));
        Bitmap bmp = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        Log.e(TAG, "onCreate: Bitmap Null=" + (bmp == null) + " imageVIew Null=" + (mImageView==null));
        mImageView.setImageBitmap(bmp);
    }

    public void closeEditPhotoActivity(View view) {
        super.onBackPressed();
    }
}
