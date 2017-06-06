package com.example.android.snoopyea;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnUser, btnEvents, btnOrder, btnCalendar, btnTeam, btnRequisites;
    TextView txtSnoopy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);


        txtSnoopy = (TextView) findViewById(R.id.txtSnoopy);
        btnUser = (Button) findViewById(R.id.btnUser);
        btnEvents = (Button) findViewById(R.id.btnEvents);
        btnOrder = (Button) findViewById(R.id.btnOrder);
        btnCalendar = (Button) findViewById(R.id.btnCalendar);
        btnTeam = (Button) findViewById(R.id.btnTeam);
        btnRequisites = (Button) findViewById(R.id.btnRequisites);

        txtSnoopy.setOnClickListener(this);
        btnUser.setOnClickListener(this);
        btnEvents.setOnClickListener(this);
        btnOrder.setOnClickListener(this);
        btnCalendar.setOnClickListener(this);
        btnTeam.setOnClickListener(this);
        btnRequisites.setOnClickListener(this);
    }

    public void eventsButtonClick(View view) {
        Toast.makeText(getApplicationContext(), "Button CLicked", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.txtSnoopy: {
                if (txtSnoopy.getText() == "SNOOPY")
                    txtSnoopy.setText("СНУПІ");
                else
                    txtSnoopy.setText("SNOOPY");

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
}

