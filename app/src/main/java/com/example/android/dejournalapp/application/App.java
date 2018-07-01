package com.example.android.dejournalapp.application;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by DELL PC on 6/29/2018.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
