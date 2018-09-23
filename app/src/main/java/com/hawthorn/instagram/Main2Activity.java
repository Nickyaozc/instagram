package com.hawthorn.instagram;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.hawthorn.instagram.Activity.ActivityFragment;
import com.hawthorn.instagram.Discovery.DiscoveryFragment;
import com.hawthorn.instagram.Home.HomeFragment;
import com.hawthorn.instagram.Photo.PhotoActivity;
import com.hawthorn.instagram.Profile.ProfileFragment;

public class Main2Activity extends AppCompatActivity {

    //const
    static final String TAG = "PhotoActivity";

    //var

    //widget
    private FrameLayout mainFrameLayout;
    private BottomNavigationView bottomNavigationView;
    private HomeFragment homeFragment;
    private DiscoveryFragment discoveryFragment;
    private ActivityFragment activityFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_bar);
        mainFrameLayout = (FrameLayout) findViewById(R.id.container);
        homeFragment = new HomeFragment();
        discoveryFragment = new DiscoveryFragment();
        activityFragment = new ActivityFragment();
        profileFragment = new ProfileFragment();

        setFragment(homeFragment);
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

    private void setUpBottomNavigationListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            setFragment(homeFragment);
                            return true;

                        case R.id.nav_discovery:
                            setFragment(discoveryFragment);
                            return true;

                        case R.id.nav_photo:
                            showPhotoActivity();
                            return false;

                        case R.id.nav_activity:
                            setFragment(activityFragment);
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
}
