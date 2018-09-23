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
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        setupViewPaper();
        tbCenterTextView = (TextView) findViewById(R.id.toolbarCenterTextView);
        Log.d(TAG, "onCreate: Started");
        setupPageChangeListener();
//        dispatchTakePictureIntent();
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
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

//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        /*Ensure that there's a camera activity to handle the intent:
//        the startActivityForResult() method is protected by a condition
//        that calls resolveActivity(), which returns the first activity component
//        that can handle the intent.*/
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "com.hawthorn.instagram.fileprovider",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//            }
//        }
//    }
//
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        System.out.println("@@@@@@@@@@@" + storageDir);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",     /* suffix */
//                storageDir      /* directory */
//        );
//        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = image.getAbsolutePath();
//        return image;
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode,
//                                    Intent data) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE) {
//            ImageView imageView = (ImageView) findViewById(R.id.myImageView);
//            Glide.with(this).load(mCurrentPhotoPath).into(imageView);
//        }
//    }
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
