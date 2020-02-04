package com.dutinfo.boisnard.tp12.Tasks;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "intitule")
    private String intitule;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "duree")
    private String duree;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "color")
    private int color;

    @ColumnInfo(name = "completed")
    private boolean completed;

    public Task(int uid, String intitule, String description, String duree, String date, int color, boolean completed) {
        this.uid = uid;
        this.intitule = intitule;
        this.description = description;
        this.duree = duree;
        this.date = date;
        this.color = color;
        this.completed = completed;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
