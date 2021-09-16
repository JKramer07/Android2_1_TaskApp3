package com.geek.android2_1_taskapp.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.geek.android2_1_taskapp.models.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();
}
