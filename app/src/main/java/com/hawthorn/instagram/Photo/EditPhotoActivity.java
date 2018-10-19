package com.hawthorn.instagram.Photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hawthorn.instagram.R;
import com.hawthorn.instagram.Utils.BitmapUtils;
import com.xw.repo.BubbleSeekBar;
import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
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
    private Bitmap filteredBmp = null;
    Bitmap currentBmp;

    //widgets
    private ImageView mImageView;
    private ImageView filterImageView1;
    private ImageView filterImageView2;
    private ImageView filterImageView3;
    private ImageView brightnessContrastBtn;
    private TextView toolbarCancelTextView;
    private TextView toolbarNextTextView;
    private TextView editCancelTextView;
    private TextView editDoneTextView;
    private BubbleSeekBar brightnessSeekBar;
    private BubbleSeekBar contrastSeekbar;
    private TextView brightnessTextView;
    private TextView contrastTextView;

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
        brightnessContrastBtn = (ImageView) findViewById(R.id.brightness_contrast);
        toolbarCancelTextView = (TextView) findViewById(R.id.toolbarCancelTextView);
        toolbarNextTextView = (TextView) findViewById(R.id.toolbarNextTextView);
        editCancelTextView = (TextView) findViewById(R.id.edit_cancel_text);
        editDoneTextView = (TextView) findViewById(R.id.edit_done_text);
        brightnessSeekBar = (BubbleSeekBar) findViewById(R.id.brightness_seekbar);
        contrastSeekbar = (BubbleSeekBar) findViewById(R.id.contrast_seekbar);
        brightnessTextView = (TextView) findViewById(R.id.brightness_textview);
        contrastTextView = (TextView) findViewById(R.id.contrast_textview);

        //set Listener
        filterImageView1.setOnClickListener(filterOnClickListener);
        filterImageView2.setOnClickListener(filterOnClickListener);
        filterImageView3.setOnClickListener(filterOnClickListener);
        brightnessContrastBtn.setOnClickListener(brtCstOnClickListener);
        editCancelTextView.setOnClickListener(editTextOnClickListener);
        editDoneTextView.setOnClickListener(editTextOnClickListener);
        brightnessSeekBar.setOnProgressChangedListener(onProgressChangedListener);
        contrastSeekbar.setOnProgressChangedListener(onProgressChangedListener);

        //obtain image from previous activity
        imageByteArray = getIntent().getByteArrayExtra(getString(R.string.cropped_image));
        bmp = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);

        //convert immutable bitmap to mutable one
        currentBmp = bmp.copy(Bitmap.Config.ARGB_8888, true);;

        editedBmp = currentBmp;

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

    private View.OnClickListener brtCstOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            filterImageView1.setVisibility(View.INVISIBLE);
            filterImageView2.setVisibility(View.INVISIBLE);
            filterImageView3.setVisibility(View.INVISIBLE);
            toolbarCancelTextView.setVisibility(View.INVISIBLE);
            toolbarNextTextView.setVisibility(View.INVISIBLE);
            editCancelTextView.setVisibility(View.VISIBLE);
            editDoneTextView.setVisibility(View.VISIBLE);
            brightnessTextView.setVisibility(View.VISIBLE);
            contrastTextView.setVisibility(View.VISIBLE);
            brightnessSeekBar.setVisibility(View.VISIBLE);
            contrastSeekbar.setVisibility(View.VISIBLE);
            brightnessSeekBar.setVerticalScrollbarPosition(0);
            contrastSeekbar.setVerticalScrollbarPosition(0);
        }
    };

    private View.OnClickListener editTextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            editCancelTextView.setVisibility(View.INVISIBLE);
            editDoneTextView.setVisibility(View.INVISIBLE);
            brightnessTextView.setVisibility(View.INVISIBLE);
            contrastTextView.setVisibility(View.INVISIBLE);
            brightnessSeekBar.setVisibility(View.INVISIBLE);
            contrastSeekbar.setVisibility(View.INVISIBLE);
            toolbarCancelTextView.setVisibility(View.VISIBLE);
            toolbarNextTextView.setVisibility(View.VISIBLE);
            filterImageView1.setVisibility(View.VISIBLE);
            filterImageView2.setVisibility(View.VISIBLE);
            filterImageView3.setVisibility(View.VISIBLE);
            switch (view.getId()) {
                case R.id.edit_cancel_text:
                    mImageView.setImageBitmap(currentBmp);
                    break;
                case R.id.edit_done_text:
                    currentBmp = editedBmp;
                    mImageView.setImageBitmap(editedBmp);
            }
        }
    };

    private BubbleSeekBar.OnProgressChangedListener onProgressChangedListener
            = new BubbleSeekBar.OnProgressChangedListener() {
        @Override
        public void onProgressChanged(
                BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
            switch (bubbleSeekBar.getId()) {
                case R.id.brightness_seekbar:
                    Log.e(TAG, "onProgressChanged: brightness=" + progress);
                    Filter brightnessFilter = new Filter();
                    brightnessFilter.addSubFilter(new BrightnessSubfilter(progress/20));
                    editedBmp = brightnessFilter.processFilter(currentBmp);
                    mImageView.setImageBitmap(editedBmp);
                    break;

                case R.id.contrast_seekbar:
                    Log.e(TAG, "onProgressChanged: contrast=" + progressFloat);
                    Filter contrastFilter = new Filter();
                    contrastFilter.addSubFilter(new ContrastSubfilter(progressFloat));
                    editedBmp = contrastFilter.processFilter(currentBmp);
                    mImageView.setImageBitmap(editedBmp);
                    break;
            }
        }

        @Override
        public void getProgressOnActionUp(
                BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

        }

        @Override
        public void getProgressOnFinally(
                BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

        }
    };

    private Bitmap setFilter(Bitmap inputImage, int filterRef) {

        inputImage = inputImage.copy(Bitmap.Config.ARGB_8888, true);
        switch (filterRef) {
            case NORMAL:
                filteredBmp = bmp;
                break;

            case BLUE_MESS:
                filteredBmp =  SampleFilters.getBlueMessFilter().processFilter(inputImage);
                break;

            case NIGHT_WHISPER:
                filteredBmp = SampleFilters.getNightWhisperFilter().processFilter(inputImage);
                break;

            case LIME_STUTTER:
                filteredBmp = SampleFilters.getLimeStutterFilter().processFilter(inputImage);
                break;
        }
        return filteredBmp;
    }

    public void showShareActivity(View view) {
        Log.e(TAG, "showShareActivity: navigate to the shareActivity");
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        currentBmp.compress(Bitmap.CompressFormat.JPEG, 100, os);
        byte[] cropeedImageBytesArray = os.toByteArray();
        Intent intent = new Intent(this, ShareActivity.class);
        intent.putExtra(getString(R.string.edited_image), cropeedImageBytesArray);
        startActivity(intent);
    }

    public void closeEditPhotoActivity(View view) {
        super.onBackPressed();
    }
}
