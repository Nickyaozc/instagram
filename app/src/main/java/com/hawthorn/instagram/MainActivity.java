package com.hawthorn.instagram;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hawthorn.instagram.Activity.ActivityMasterFragment;
import com.hawthorn.instagram.Discovery.DiscoveryFragment;
import com.hawthorn.instagram.Home.HomeFragment;
import com.hawthorn.instagram.Login.LoginActivity;
import com.hawthorn.instagram.Photo.PhotoActivity;
import com.hawthorn.instagram.Profile.ProfileActivity;
import com.hawthorn.instagram.Profile.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    //const
    static final String TAG = "MainActivity";

    //var

    //widget
    private FrameLayout mainFrameLayout;
    private BottomNavigationView bottomNavigationView;
    private HomeFragment homeFragment;
    private DiscoveryFragment discoveryFragment;
    private ActivityMasterFragment activityFragment;
    private ProfileFragment profileFragment;
    private Toolbar mToolbar;

    //firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: starting");

        setupFirebaseAuth();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_bar);
        mainFrameLayout = (FrameLayout) findViewById(R.id.container);
        mToolbar = findViewById(R.id.my_toolbar);
        homeFragment = new HomeFragment();
        discoveryFragment = new DiscoveryFragment();
        activityFragment = new ActivityMasterFragment();
        profileFragment = new ProfileFragment();
        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);

//        setFragment(homeFragment);
//        mToolbar.setLogo(R.drawable.instagram_textlogo);
        setUpBottomNavigationListener();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    public void showPhotoActivity() {
        Intent intent = new Intent(this, PhotoActivity.class);
        startActivity(intent);
    }

    public void showProfileActivity(){
        Intent intent1 = new Intent(this, ProfileActivity.class);
        startActivity(intent1);
    }

    private void setUpBottomNavigationListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
//                            mToolbar.setLogo(R.drawable.instagram_textlogo);
                            setFragment(homeFragment);
                            return true;

                        case R.id.nav_discovery:
//                            mToolbar.setLogo(null);
                            setFragment(discoveryFragment);
                            return true;

                        case R.id.nav_photo:
//                            mToolbar.setLogo(null);
                            showPhotoActivity();
                            return false;

                        case R.id.nav_activity:
//                            mToolbar.setLogo(null);
                            setFragment(activityFragment);
                            setActivityToolbar();
                            return true;

                        case R.id.nav_profile:
                            setFragment(profileFragment);
                            return true;

                        default:
                            return false;
                    }
                }
            });
    }

    private void setActivityToolbar() {
//        mToolbar.
    }

    

    /*
    ---------------------------------------- Firebase ---------------------------------------------
     */

    /**
     * set up Firebase auth object
     */
    private void setupFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        checkCurrentUser(currentUser);
    }

    private void checkCurrentUser(FirebaseUser user) {
        Log.d(TAG, "checkCurrentUser: starting");
        if (user != null) {
            // User is signed in
            Log.d(TAG, "checkCurrentUser: signed_in: " + user.getUid());
            setFragment(homeFragment);
        } else {
            // User is signed out
            Log.d(TAG, "checkCurrentUser: signed_out");
            showLoginActivity();
        }
    }

    private void showLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
