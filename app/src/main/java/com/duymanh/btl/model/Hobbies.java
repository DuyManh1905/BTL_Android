package com.duymanh.btl.model;

import java.io.Serializable;

public class Hobbies implements Serializable {
    private Integer id;

    private String name;

    public Hobbies(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Hobbies{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Hobbies(String name) {
        this.name = name;
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
}
