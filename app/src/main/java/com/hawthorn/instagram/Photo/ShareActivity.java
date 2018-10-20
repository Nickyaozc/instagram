package com.hawthorn.instagram.Photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hawthorn.instagram.R;
import com.hawthorn.instagram.Utils.FirebaseMethods;

public class ShareActivity extends AppCompatActivity {

    private final static String TAG = "ShareActivity";

    //widgets
    private ImageView mImageView;
    private EditText mEditText;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;

    //varx
    private String mAppend = "file:/";
    private int imageCount = 0;
    private Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "shareActivity: started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        mFirebaseMethods = new FirebaseMethods(ShareActivity.this);

        //init views
        mImageView = (ImageView) findViewById(R.id.imageShare);
        mEditText = (EditText) findViewById(R.id.caption);

        setupFirebaseAuth();

        //obtain image from previous activity
        byte[] imageByteArray = getIntent().getByteArrayExtra(getString(R.string.edited_image));
        bmp = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        mImageView.setImageBitmap(bmp);

    }

    public void closeShareActivity(View view) {
        super.onBackPressed();
    }

    public void share(View view) {
        Log.d(TAG, "onClick: navigating to the final share screen.");
        //upload the image to firebase
        Toast.makeText(ShareActivity.this, "Attempting to upload new photo", Toast.LENGTH_SHORT).show();
        String caption = mEditText.getText().toString();
        mFirebaseMethods.uploadNewPhoto(getString(R.string.new_photo), caption, imageCount, null, bmp);
    }




    // --------------------------- firebase ------------------------------
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        Log.d(TAG, "onDataChange: image count: " + imageCount);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                imageCount = mFirebaseMethods.getImageCount(dataSnapshot);
                Log.d(TAG, "onDataChange: image count: " + imageCount);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
