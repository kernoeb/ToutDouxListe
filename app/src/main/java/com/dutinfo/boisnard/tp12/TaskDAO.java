package com.dutinfo.boisnard.tp12;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.dutinfo.boisnard.tp12.tasks.Task;

import java.util.List;

@Dao // Task DAOs
public interface TaskDAO {
    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Insert
    void insertAll(Task... tasks);

    @Delete
    void delete(Task task);

    @Query("UPDATE task SET intitule = :intitule, description = :description, duree = :duree, date = :date, color = :color, url = :url WHERE uid = :itemId")
    void editAll(String intitule, String description, String duree, String date, int color, String url, long itemId);

    @Query("UPDATE task SET completed = :completed WHERE uid = :itemId")
    void editCompleted(boolean completed, long itemId);

}