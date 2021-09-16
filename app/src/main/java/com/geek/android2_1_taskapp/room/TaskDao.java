package com.geek.android2_1_taskapp.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.geek.android2_1_taskapp.models.Task;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAll();

    @Insert
    void insert(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM task")
    List<Task> deleteAll();

    @Update
    void update(Task task);

    @Query("SELECT * FROM task")
    List<Task> updateAll();

    @Query("SELECT * FROM task order by text ASC")
    LiveData<List<Task>> getAllByText();
}
