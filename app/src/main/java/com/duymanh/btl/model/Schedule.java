package com.duymanh.btl.model;

import java.io.Serializable;

public class Schedule implements Serializable {
    private int id;
    private String title,category,time,date;

    public Schedule() {
    }

    public Schedule(int id, String title, String category, String time, String date) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.time = time;
        this.date = date;
    }

    public Schedule(String title, String category, String time, String date) {
        this.title = title;
        this.category = category;
        this.time = time;
        this.date = date;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
