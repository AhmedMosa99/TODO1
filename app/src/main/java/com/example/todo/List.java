package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class List extends AppCompatActivity {
    RecyclerView tasks_rv;
    TaskAdapterEx  taskAdapter;
    static java.util.List<Task> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        taskList.add(new Task(1,"Study Programming"));
        taskList.add(new Task(2,"Study"));
        taskList.add(new Task(3,"Programming"));
        tasks_rv = findViewById(R.id.tasks_rv);
        tasks_rv.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapterEx(taskList);
        tasks_rv.setAdapter(taskAdapter);
    }
}