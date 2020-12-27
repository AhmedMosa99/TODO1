package com.example.todo;

public class Task {
    String Title;

    int count;

    public Task() {
    }

    public Task(int count,String title) {
        this.Title = title;
    this.count=count;
    }

    public int getCount() {
        return count;
    }

    public void setCount( String id) {
        this.count = count;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }




}