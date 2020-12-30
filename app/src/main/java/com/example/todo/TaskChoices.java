package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TaskChoices extends AppCompatActivity implements CheckTaskAdapter.ListItemClickListener {
    RecyclerView choice_rv;
    CheckTaskAdapter  taskAdapter;
  ImageView addTask;
    EditText TaskDescription, TaskTitle;
    String categoryId,categoryTitle;
    TextView cateTitle;
    private FirebaseAuth mAuth;
    static List<CheckTask> CheckList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_choices);
        Bundle extras = getIntent().getExtras();
        categoryId = extras.getString("CATEGORY_ID");
        categoryTitle=extras.getString("CATEGORY_TITLE");
        cateTitle=findViewById(R.id.cateTitle);
        cateTitle.setText(categoryTitle);
        addTask=findViewById(R.id.addTask);
        TaskDescription = findViewById(R.id.TaskDescription);
        TaskTitle = findViewById(R.id.TaskTitle);
        addTask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();
                CheckTask newTask = new CheckTask();
                newTask.setTitle(TaskTitle.getText().toString());
                newTask.setDescription(TaskDescription.getText().toString());
                newTask.setIsChecked(false);
                String taskId = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("task").child(categoryId).child("innerTask").push().getKey();
                newTask.setId(taskId);
                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("task").child(categoryId).child("innerTask").child(taskId).setValue(newTask);
                Toast.makeText(TaskChoices.this,"added successfully", Toast.LENGTH_SHORT).show();
                TaskTitle.setText("");
                TaskDescription.setText("");
            }
        });
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("task").child(categoryId).child("innerTask")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        CheckList.clear();
                        for(DataSnapshot snapshot: dataSnapshot.getChildren() ){
                            CheckTask item =  snapshot.getValue(CheckTask.class);
                            CheckList.add(item);
                        }
                        taskAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
//        CheckList.add(new CheckTask("1","Meeting with client" ,false,"ahmed"));
//        CheckList.add(new CheckTask("2","Meeting with AHmed" ,true,"ahmed"));
//        CheckList.add(new CheckTask("3","Meeting with Omer" ,false,"ahmed"));
        choice_rv = findViewById(R.id.choice_rv);
        choice_rv.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new CheckTaskAdapter (this,CheckList,  this);
        choice_rv.setAdapter(taskAdapter);

    }


    @Override
    public void onListItemClick(int position) {

    }
}
