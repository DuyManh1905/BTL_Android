package com.duymanh.btl.model;

import java.io.Serializable;

public class CateJob implements Serializable {
    private int id;
    private String name;
    private String major;

    // Getters and Setters

    public CateJob(int id, String name, String major) {
        this.id = id;
        this.name = name;
        this.major = major;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}