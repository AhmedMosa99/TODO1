package com.example.todo;

public class Task {
    String Title;
 String id;
    int count;

    public Task() {
    }

    public Task(int count,String title ,String id) {
        this.Title = title;
    this.count=count;
    this.id=id;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public int getCount() {
        return count;
    }

    public void setCount( int count) {
        this.count = count;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }




}