package com.hawthorn.instagram.Photo;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.hawthorn.instagram.R;

public class PhotoActivity extends FragmentActivity {
    //var, const
    static final String TAG = "PhotoActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private static final int NUM_PAGES = 2;

    //widgets
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private TextView tbCenterTextView;
    private TextView tbCancelTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_photo);
        setupViewPaper();
        tbCenterTextView = (TextView) findViewById(R.id.toolbarCenterTextView);
        tbCancelTextView = (TextView) findViewById(R.id.toolbarCancelTextView);
        Log.d(TAG, "onCreate: Started");
        setupPageChangeListener();
    }



    public void closePhotoActivity(View view) {
        super.onBackPressed();
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
                return new GalleryFragment();
            }else {
                return new CameraFragment();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

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
                }else{
                    tbCenterTextView.setText("camera");
                }
            }
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

}
