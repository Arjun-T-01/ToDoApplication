package com.arjunt.firstproject.todo.DataModel;

public class ToDoItem {
    int id;
    String title,date,time;

    public ToDoItem() {
    }

    public ToDoItem(int id, String title, String data, String time) {
        this.id = id;
        this.title = title;
        this.date = data;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String data) {
        this.date = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
