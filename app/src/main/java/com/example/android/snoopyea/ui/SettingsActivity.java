package com.example.android.snoopyea.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.snoopyea.R;
import com.example.android.snoopyea.models.User;
import com.example.android.snoopyea.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    private final String TAG = "SettingActivity.java";

    Calendar mBirthDay = Calendar.getInstance();

    Button btnLogout, btnSaveChanges;
    EditText inputEmail, inputFirstName, inputSecondName, inputLastName, inputPhone, inputBDay;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseRefCurrentUser;
    private ValueEventListener mUserListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        setContentView(R.layout.activity_settings);

        initializeReferences();

        initializeCurrentUserData();

    }

    private void initializeReferences() {
        // References
        inputFirstName = (EditText) findViewById(R.id.firstName);
        inputSecondName = (EditText) findViewById(R.id.secondName);
        inputLastName = (EditText) findViewById(R.id.lastName);
        inputPhone = (EditText) findViewById(R.id.phone);
        inputEmail = (EditText) findViewById(R.id.email);
        inputBDay = (EditText) findViewById(R.id.bday);
        btnSaveChanges = (Button) findViewById(R.id.btnSaveChanges);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        // Focus settings
        inputEmail.setNextFocusDownId(R.id.btnSaveChangesFrame);
        findViewById(R.id.btnSaveChangesFrame).requestFocus();

        // OnClickListeners for buttons
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();

            }
        });
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });

        // Save selected date
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mBirthDay.set(Calendar.YEAR, year);
                mBirthDay.set(Calendar.MONTH, monthOfYear);
                mBirthDay.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.US);

                inputBDay.setText(sdf.format(mBirthDay.getTime()));
                findViewById(R.id.btnSaveChangesFrame).requestFocus();
            }
        };

        // Pop up the Date Picker after user clicked on editText
        inputBDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SettingsActivity.this, dateSetListener,
                        mBirthDay.get(Calendar.YEAR), mBirthDay.get(Calendar.MONTH),
                        mBirthDay.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void saveChanges() {

        final String firstName = inputFirstName.getText().toString().trim();
        final String secondName = inputSecondName.getText().toString().trim();
        final String lastName = inputLastName.getText().toString().trim();
        final String phone = inputPhone.getText().toString().trim();
        final String email = inputEmail.getText().toString().trim();
        final String bday = inputBDay.getText().toString().trim();

        User user= new User(firstName, secondName, lastName,phone, email, bday);

        Map<String, Object> userValue = user.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("userFirstName", firstName);
        childUpdates.put("userSecondName", secondName);
        childUpdates.put("userLastName", lastName);
        childUpdates.put("userPhone", phone);
        childUpdates.put("userBDay", bday);

        // TODO update Email!


        mDatabaseRefCurrentUser.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(SettingsActivity.this, R.string.toast_data_updated, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeCurrentUserData() {


        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            mDatabaseRefCurrentUser = mDatabase.getReference(Constants.FIREBASE_REF_USERS).child(currentUser.getUid());

            mUserListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);

                    inputFirstName.setText(user.getUserFirstName());
                    inputSecondName.setText(user.getUserSecondName());
                    inputLastName.setText(user.getUserLastName());
                    inputPhone.setText(user.getUserPhone());
                    inputEmail.setText(user.getUserEmail());
                    inputBDay.setText(user.getUserBDay());

                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

                    Date dt = new Date();
                    try {
                        dt = sdf.parse(user.getUserBDay());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    mBirthDay.setTime(dt);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    Log.w(TAG, "Failed to load data. " + databaseError.toException());
                }
            };

            mDatabaseRefCurrentUser.addValueEventListener(mUserListener);
        } else {
            Log.w(TAG, "Error. User is not logged in");
            onDestroy();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabaseRefCurrentUser.addValueEventListener(mUserListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mUserListener != null) {
            mDatabaseRefCurrentUser.removeEventListener(mUserListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        switch (id) {
            case R.id.action_logout: {
                // User chose the "Settings" item, show the app settings UI...
                logout();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_logout_title)
                .setMessage(R.string.dialog_logout_message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        mAuth.signOut();
                        Intent intent = new Intent();
                        intent.putExtra(Constants.EXTRA_NEED_LOGOUT, true);
                        setResult(RESULT_OK, intent);
                        onBackPressed();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                //alert icon if we need
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }


}
