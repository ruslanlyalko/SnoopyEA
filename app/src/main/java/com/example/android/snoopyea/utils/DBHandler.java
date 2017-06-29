package com.example.android.snoopyea.utils;

import android.support.annotation.NonNull;

import com.example.android.snoopyea.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DBHandler {


    private static DBHandler DBHandler;
    private FirebaseUser mCurrentUser;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    //private static DatabaseReference mDatabaseRefCurrentUser;
    private User mUser;

    private DBHandler() {
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mCurrentUser = firebaseAuth.getCurrentUser();
                if (mCurrentUser != null)
                    refreshUser(mCurrentUser.getUid());
                else{
                    mUser = null;
                }
            }
        });
    }

    /*
     * Return instance of DBHandler class.
     */
    private static DBHandler getInstance() {
        if (DBHandler == null) {
            DBHandler = new DBHandler();
        }

        return DBHandler;
    }

    private void refreshUser(String uId) {

        DatabaseReference mDatabaseRefCurrentUser = mDatabase.getReference(Constants.FIREBASE_REF_USERS)
                .child(uId);

        mDatabaseRefCurrentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUser = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mUser = null;
            }

        });

    }

    /*
     *   GETTERS
     */

    public User getUser() {

        return mUser;
    }


}
