package com.example.android.snoopyea.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.snoopyea.R;
import com.example.android.snoopyea.utils.SwipeLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String DEBUG_TAG = "MainActivity.java";
    Button btnUser, btnEvents, btnOrder, btnCalendar, btnTeam, btnRequisites, btnSwipe;
    TextView txtSnoopy;
    SwipeLayout swipeLayout;

    boolean doubleBackToExitPressedOnce = false;
    boolean swipeOpened = false;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        initializeReferences();

        // Enable connection without internet
        mDatabase.setPersistenceEnabled(true);

        swipeLayout.setBottomSwipeEnabled(false);
        swipeLayout.setTopSwipeEnabled(false);

        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {
                btnSwipe.setText("\\/");
            }

            @Override
            public void onOpen(SwipeLayout layout) {
                btnSwipe.setText("\\/");
                swipeOpened = true;
            }

            @Override
            public void onStartClose(SwipeLayout layout) {
                btnSwipe.setText("/\\");
            }

            @Override
            public void onClose(SwipeLayout layout) {
                btnSwipe.setText("/\\");
                swipeOpened = false;
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        btnSwipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swipeOpened)
                    swipeLayout.close();
                 else
                    swipeLayout.open();

            }
        });
    }

    /**
     * Set references to Buttons etc
     */
    private void initializeReferences() {
        txtSnoopy = (TextView) findViewById(R.id.txtSnoopy);
        btnUser = (Button) findViewById(R.id.btnUser);
        btnEvents = (Button) findViewById(R.id.btnEvents);
        btnOrder = (Button) findViewById(R.id.btnOrder);
        btnCalendar = (Button) findViewById(R.id.btnCalendar);
        btnTeam = (Button) findViewById(R.id.btnTeam);
        btnRequisites = (Button) findViewById(R.id.btnRequisites);
        btnSwipe = (Button) findViewById(R.id.btnSwipe);
        swipeLayout = (SwipeLayout) findViewById(R.id.godfather);


        txtSnoopy.setOnClickListener(this);
        btnUser.setOnClickListener(this);
        btnEvents.setOnClickListener(this);
        btnOrder.setOnClickListener(this);
        btnCalendar.setOnClickListener(this);
        btnTeam.setOnClickListener(this);
        btnRequisites.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.txtSnoopy: {
                if (txtSnoopy.getText() != "SNOOPY")
                    txtSnoopy.setText("SNOOPY");
                else
                    txtSnoopy.setText("СНУПІ");

                break;
            }
            case R.id.btnUser: {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnEvents: {
                Intent intent = new Intent(this, EventsActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.btnOrder: {

                break;
            }
            case R.id.btnCalendar: {

                break;
            }
            case R.id.btnTeam: {

                break;
            }
            case R.id.btnRequisites: {

                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            // TODO (kostul) close application completely
            int pid = android.os.Process.myPid();
            android.os.Process.killProcess(pid);
            return;
        }
        // Swipe down
        if(swipeOpened)
        {
            swipeLayout.close();
            swipeOpened = false;
            return;
        }

        // Close app after twice click on Back button
        doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.hint_double_press, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}

