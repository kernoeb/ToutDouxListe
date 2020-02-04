package com.dutinfo.boisnard.tp12.Tasks;

import java.util.ArrayList;

public class TaskGenerator {
    public ArrayList<Task> getTaskArrayList() {
        return taskArrayList;
    }

    private ArrayList<Task> taskArrayList;

    public TaskGenerator() {
        this.taskArrayList = new ArrayList<Task>();
    }


}
