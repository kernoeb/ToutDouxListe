package com.dutinfo.boisnard.tp12;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.dutinfo.boisnard.tp12.Tasks.Task;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Dao
public interface TaskDAO {
    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task WHERE uid = (:userIds)")
    Task getById(int userIds);

    @Insert
    void insertAll(Task... tasks);

    @Delete
    void delete(Task task);

    //Delete one item by id
    @Query("DELETE FROM task WHERE uid = :itemId")
    void deleteByItemId(long itemId);

    @Query("UPDATE task SET intitule = :intitule WHERE uid = :itemId")
    void editIntitule(String intitule, long itemId);

    @Query("UPDATE task SET description = :description WHERE uid = :itemId")
    void editDescription(String description, long itemId);

    @Query("UPDATE task SET duree = :duree WHERE uid = :itemId")
    void editDuree(String duree, long itemId);

    @Query("UPDATE task SET date = :date WHERE uid = :itemId")
    void editDate(String date, long itemId);

    @Query("UPDATE task SET completed = :completed WHERE uid = :itemId")
    void editCompleted(boolean completed, long itemId);

    @Query("SELECT completed FROM task WHERE uid = :itemId")
    boolean isCompleted(long itemId);
}