package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class List extends AppCompatActivity implements TaskAdapterEx.ListItemClickListener{
    RecyclerView tasks_rv;
    TaskAdapterEx  taskAdapter;
    private FirebaseAuth mAuth;
    EditText Ltitle;
    Button AddTask;
    String categoryId, categoryTitle;
    Integer categoryCount = 0;
    static java.util.List<Task> categoriesTask = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
       tasks_rv=findViewById(R.id.tasks_rv);
       Ltitle=findViewById(R.id.Ltitle);
       AddTask=findViewById(R.id.AddTask);


        AddTask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();
                Task newTask=new Task();
                newTask.setTitle(Ltitle.getText().toString());
                newTask.setCount(0);
                String categoryId = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("task").push().getKey();
                newTask.setId(categoryId);
                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("task").child(categoryId).setValue(newTask);
                Toast.makeText(List.this,"Category has been added successfully", Toast.LENGTH_SHORT).show();
                Ltitle.setText("");
            }
        });
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("task")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        categoriesTask.clear();
                        for(DataSnapshot snapshot: dataSnapshot.getChildren() ){
                            Task item =  snapshot.getValue(Task.class);
                            categoriesTask.add(item);

                        }
                        taskAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
        tasks_rv = findViewById(R.id.tasks_rv);
        tasks_rv.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapterEx(categoriesTask,  this);
        tasks_rv.setAdapter(taskAdapter);
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(List.this,"signed out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(List.this, Login.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(List.this, TaskChoices.class);
        intent.putExtra("CATEGORY_ID", categoriesTask.get(position).getId());
        intent.putExtra("CATEGORY_TITLE", categoriesTask.get(position).getTitle());
        startActivity(intent);
    }
}
