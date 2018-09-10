package com.hawthorn.instagram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void signUp(View view) {

        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        if(usernameEditText.getText().toString().equals("") ||
                passwordEditText.getText().toString().equals("")) {
            Toast.makeText(this, "a username or password are required",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "sign up successfully", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
