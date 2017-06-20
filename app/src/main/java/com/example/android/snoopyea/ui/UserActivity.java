package com.example.android.snoopyea.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.snoopyea.R;
import com.example.android.snoopyea.models.User;
import com.example.android.snoopyea.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {

    private final String TAG = "UserActivity.java";
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRefCurrentUser;

    ProgressBar progressBar;
    Button btnLogout;
    TextView textUserName;
    TextView textUserEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        setContentView(R.layout.activity_user);


        //Get Firebase auth instance
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        // Set btn's, text's references and onClickListener's
        initializeReferences();

        initializeCurrentUserData();

    }

    private void initializeReferences() {
        btnLogout = (Button) findViewById(R.id.btn_logout);
        textUserName = (TextView) findViewById(R.id.textUserName);
        textUserEmail = (TextView) findViewById(R.id.textUserEmail);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.signOut();
                onBackPressed();
            }
        });

    }

    private void initializeCurrentUserData() {

        progressBar.setVisibility(View.VISIBLE);

        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if (currentUser != null) {
            mDatabaseRefCurrentUser = mFirebaseDatabase.getReference(Constants.FIREBASE_TABLE_USERS).child(currentUser.getUid());

            mDatabaseRefCurrentUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);

                    textUserName.setText(user.getUserName());
                    textUserEmail.setText(user.getUserEmail());
                    progressBar.setVisibility(View.GONE);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    textUserName.setText("");
                    textUserEmail.setText("");
                    Log.w(TAG, "Failed to load data. " + databaseError.toException());
                }
            });

        } else {
            Log.w(TAG, "Error. User is not logged in");
            onDestroy();
        }
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

