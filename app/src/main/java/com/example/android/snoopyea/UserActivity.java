package com.example.android.snoopyea;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class UserActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    Button btnLogout;

    TextView textUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_user);

        btnLogout = (Button) findViewById(R.id.btn_logout);
        textUserName = (TextView) findViewById(R.id.textUserName);

        textUserName.setText(auth.getCurrentUser().getEmail().toString());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                onBackPressed();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_settings: {
                // User chose the "Settings" item, show the app settings UI...
                //startActivity(new Intent(UserActivity.this, UserSettingsActivity.class));
            }
            return true;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }
}

