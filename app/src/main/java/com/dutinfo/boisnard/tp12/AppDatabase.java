package com.dutinfo.boisnard.tp12;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.dutinfo.boisnard.tp12.tasks.Task;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDAO taskDAO();
}