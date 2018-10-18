package com.hawthorn.instagram.Photo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.hawthorn.instagram.R;
import com.hawthorn.instagram.Utils.CustomViewPager;

import org.w3c.dom.Text;

public class PhotoActivity extends FragmentActivity {
    //var, const
    static final String TAG = "PhotoActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private static final int NUM_PAGES = 2;

    //widgets
    private CustomViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private TextView tbCenterTextView;
    private TextView tbCancelTextView;
    private TextView tbNextTextView;
    private Toolbar mToolbar;
    private GalleryFragment galleryFragment;
    private LiveCameraFragment liveCameraFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        setupViewPaper();
        tbCenterTextView = (TextView) findViewById(R.id.toolbarCenterTextView);
        tbCancelTextView = (TextView) findViewById(R.id.toolbarCancelTextView);
        tbNextTextView = (TextView) findViewById(R.id.toolbarNextTextView);
        tbNextTextView.setVisibility(View.GONE);
        mToolbar = (Toolbar) findViewById(R.id.photo_toolbar);
        Log.d(TAG, "onCreate: Started");
        setupPageChangeListener();
    }

    public void closePhotoActivity(View view) {
        Log.d(TAG, "closePhotoActivity: closing the photo activity");
        super.onBackPressed();
    }

    public void showEditPhotoActivity(View view) {
        galleryFragment.crop();
        Log.d(TAG, "showEditPhotoActivity: crop and navigate to the EditPhotoActivity");
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                galleryFragment = new GalleryFragment();
                return galleryFragment;
            } else if (position == 1){
                //return new CameraFragment();
                return new LiveCameraFragment();
            } else {
                return new EditPhotoFragment();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupViewPaper() {
        mPager = findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(1);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.bottom_tabs);
        tabLayout.setupWithViewPager(mPager);

        tabLayout.getTabAt(0).setText(getString(R.string.toolbar_Gallery));
        tabLayout.getTabAt(1).setText(getString(R.string.toolbar_camera));

    }

    private void setupPageChangeListener() {
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    tbCenterTextView.setText("gallery");
                    tbNextTextView.setVisibility(View.VISIBLE);
                }else{
                    tbCenterTextView.setText("camera");
                    tbNextTextView.setVisibility(View.GONE);

                }
            }
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public TextView getTbNextTextView() {
        return tbNextTextView;
    }
}
