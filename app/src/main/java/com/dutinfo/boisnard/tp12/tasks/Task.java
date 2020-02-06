package com.dutinfo.boisnard.tp12.tasks;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    private final int uid;

    @ColumnInfo(name = "intitule")
    private final String intitule;

    @ColumnInfo(name = "description")
    private final String description;

    @ColumnInfo(name = "duree")
    private final String duree;

    @ColumnInfo(name = "date")
    private final String date;

    @ColumnInfo(name = "color")
    private final int color;

    @ColumnInfo(name = "completed")
    private boolean completed;

    @ColumnInfo(name = "url")
    private final String url;

    public Task(int uid, String intitule, String description, String duree, String date, int color, boolean completed, String url) {
        this.uid = uid;
        this.intitule = intitule;
        this.description = description;
        this.duree = duree;
        this.date = date;
        this.color = color;
        this.completed = completed;
        this.url = url;
    }

    public String getUrl() {
        return url;
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

    public int getUid() {
        return uid;
    }

    public String getIntitule() {
        return intitule;
    }

    public String getDescription() {
        return description;
    }

    public String getDuree() {
        return duree;
    }

    public String getDate() {
        return date;
    }


}
