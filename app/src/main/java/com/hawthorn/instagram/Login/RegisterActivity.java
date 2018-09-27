
package com.hawthorn.instagram.Login;

import android.app.Activity;
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
import com.hawthorn.instagram.MainActivity;
import com.hawthorn.instagram.R;

public class RegisterActivity extends Activity {

    private static final String TAG = "RegisterActivity";

    //Firebase
    private FirebaseAuth mAuth;

    //var
    EditText emailInputText;
    EditText passwordInputText;
    EditText nameInputText;
    String email;
    String password;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d(TAG, "onCreate: Started");

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
