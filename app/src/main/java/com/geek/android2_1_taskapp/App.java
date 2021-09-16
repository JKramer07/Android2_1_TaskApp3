package com.geek.android2_1_taskapp;

import android.app.Application;

import androidx.room.Room;

import com.geek.android2_1_taskapp.room.AppDatabase;

public class App extends Application {

    private static AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        appDatabase = Room
                .databaseBuilder(this, AppDatabase.class, "mydb")
                .allowMainThreadQueries()
                .build();
    }

    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }

}
