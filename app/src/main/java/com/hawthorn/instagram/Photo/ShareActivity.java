package com.hawthorn.instagram.Photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.hawthorn.instagram.R;

public class ShareActivity extends AppCompatActivity {

    private final static String TAG = "ShareActivity";

    //widgets
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "shareActivity: started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        //init views
        mImageView = (ImageView) findViewById(R.id.imageShare);

        //obtain image from previous activity
        byte[] imageByteArray = getIntent().getByteArrayExtra(getString(R.string.edited_image));
        Bitmap bmp = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        mImageView.setImageBitmap(bmp);

    }

    public void closeShareActivity(View view) {
        super.onBackPressed();
    }
}
