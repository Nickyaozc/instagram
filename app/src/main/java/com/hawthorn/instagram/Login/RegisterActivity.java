
package com.hawthorn.instagram.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hawthorn.instagram.MainActivity;
import com.hawthorn.instagram.R;
import com.hawthorn.instagram.Utils.Firebasemethods;

public class RegisterActivity extends Activity {

    private static final String TAG = "RegisterActivity";

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Firebasemethods firebaseMethods;

    //var
    EditText emailInputText;
    EditText passwordInputText;
    EditText nameInputText;
    String email;
    String password;
    String name;
    private String append = "";
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d(TAG, "onCreate: Started");
        mContext = RegisterActivity.this;
        setupFirebaseAuth();
    }

    public void register(View view) {
        emailInputText = (EditText) findViewById(R.id.emailInputText);
        passwordInputText = (EditText) findViewById(R.id.passwordInputText);
        nameInputText = (EditText) findViewById(R.id.nameInputText);
        email = emailInputText.getText().toString();
        password = passwordInputText.getText().toString();
        name = nameInputText.getText().toString();

        if(email.equals("") || password.equals("") || name.equals("")) {
            Toast.makeText(this, "a username or password are required",
                    Toast.LENGTH_SHORT).show();
        } else {
            createAccount(email, password);
        }

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //1st check: Make sure the username is not already in use
                            if(firebaseMethods.checkIfUsernameExists(name, dataSnapshot)){
                                append = myRef.push().getKey().substring(3,10);
                                Log.d(TAG, "onDataChange: username already exists. Appending random string to name: " + append);
                            }
                            name = name + append;

                            //add new user to the database
                            firebaseMethods.addNewUser(email, name, "", "", "");

                            Toast.makeText(mContext, "Signup successful. Sending verification email.", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

    }

    public void showMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

        /*
    ---------------------------------------- Firebase ---------------------------------------------
     */

    /**
     * set up Firebase auth object
     */
    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: starting");
        mAuth = FirebaseAuth.getInstance();

    }

    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            showMainActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }


}
