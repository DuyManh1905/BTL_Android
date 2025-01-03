package com.duymanh.btl.model;

import java.io.Serializable;

public class Certificates implements Serializable {
    private Integer id;

    private String name;

    private String time;

    @Override
    public String toString() {
        return "Certificates{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public Certificates(int id, String name, String time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }

    public Certificates(String name, String time) {
        this.name = name;
        this.time = time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
