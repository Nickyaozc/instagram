package com.hawthorn.instagram.Photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.hawthorn.instagram.R;
import com.hawthorn.instagram.Utils.BitmapUtils;
import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;

import java.util.MissingFormatArgumentException;

public class EditPhotoActivity extends AppCompatActivity {
    private final String TAG = "EditPhotoActivity";
    //const
    private final static int NORMAL = 0;
    private final static int BLUE_MESS = 1;
    private final static int NIGHT_WHISPER = 2;
    private final static int LIME_STUTTER = 3;


    //var
    private byte[] imageByteArray;
    private Bitmap bmp;
    private Bitmap editedBmp = null;

    //widgets
    private ImageView mImageView;
    private ImageView filterImageView1;
    private ImageView filterImageView2;
    private ImageView filterImageView3;

    //load native library
    static
    {
        System.loadLibrary("NativeImageProcessor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);
        Log.d(TAG, "onCreate: Started");

        //init
        mImageView = (ImageView) findViewById(R.id.EditImageView);
        filterImageView1 = (ImageView) findViewById(R.id.filter_1);
        filterImageView2 = (ImageView) findViewById(R.id.filter_2);
        filterImageView3 = (ImageView) findViewById(R.id.filter_3);
        filterImageView1.setOnClickListener(filterOnClickListener);
        filterImageView2.setOnClickListener(filterOnClickListener);
        filterImageView3.setOnClickListener(filterOnClickListener);

        //obtain image from previous activity
        imageByteArray = getIntent().getByteArrayExtra(getString(R.string.cropped_image));

        //convert immutable bitmap to mutable one
        bmp = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
//        bmp = bmp.copy(Bitmap.Config.ARGB_8888, true);

        //set Image to ImageView
        mImageView.setImageBitmap(bmp);
        filterImageView1.setImageBitmap(setFilter(bmp, BLUE_MESS));
        filterImageView2.setImageBitmap(setFilter(bmp, NIGHT_WHISPER));
        filterImageView3.setImageBitmap(setFilter(bmp, LIME_STUTTER));

    }

    private View.OnClickListener filterOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.filter_1:
                    mImageView.setImageBitmap(setFilter(bmp, BLUE_MESS));
                    break;
                case R.id.filter_2:
                    mImageView.setImageBitmap(setFilter(bmp, NIGHT_WHISPER));
                    break;
                case R.id.filter_3:
                    mImageView.setImageBitmap(setFilter(bmp, LIME_STUTTER));
                    break;
            }
        }
    };

    public void closeEditPhotoActivity(View view) {
        super.onBackPressed();
    }

    private Bitmap setFilter(Bitmap inputImage, int filterRef) {

        inputImage = inputImage.copy(Bitmap.Config.ARGB_8888, true);
        switch (filterRef) {
            case NORMAL:
                editedBmp = bmp;
                break;

            case BLUE_MESS:
                editedBmp =  SampleFilters.getBlueMessFilter().processFilter(inputImage);
                break;

            case NIGHT_WHISPER:
                editedBmp = SampleFilters.getNightWhisperFilter().processFilter(inputImage);
                break;

            case LIME_STUTTER:
                editedBmp = SampleFilters.getLimeStutterFilter().processFilter(inputImage);
                break;
        }
        return editedBmp;
    }


}
