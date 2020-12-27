package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class TaskChoices extends AppCompatActivity {
    RecyclerView choice_rv;
    CheckTaskAdapter  taskAdapter;
    static List<CheckTask> CheckList = new ArrayList<CheckTask>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_choices);
        CheckList.add(new CheckTask("1","Meeting with client" ,false));
        CheckList.add(new CheckTask("2","Meeting with AHmed" ,true));
        CheckList.add(new CheckTask("3","Meeting with Omer" ,false));
        choice_rv = findViewById(R.id.choice_rv);
        choice_rv.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new CheckTaskAdapter (this,CheckList);
        choice_rv.setAdapter(taskAdapter);

    }


}
