package com.hawthorn.instagram;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        BottomNavigationView bottomNavigationView
                = (BottomNavigationView) findViewById(R.id.bottom_navigation_bar);
        FrameLayout mainFrameLayout = (FrameLayout) findViewById(R.id.container);

        final HomeFragment homeFragment = new HomeFragment();
        final DiscoveryFragment discoveryFragment = new DiscoveryFragment();
        final photoFragment photoFragment = new photoFragment();
        final ActivityFragment activityFragment = new ActivityFragment();
        final ProfileFragment profileFragment = new ProfileFragment();

        setFragment(homeFragment);

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
                        setFragment(photoFragment);
                        return true;

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

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}
