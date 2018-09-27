
package com.hawthorn.instagram.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hawthorn.instagram.Main2Activity;
import com.hawthorn.instagram.R;

public class RegisterActivity extends Activity {

    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d(TAG, "onCreate: Started");
    }

    public void register(View view) {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
