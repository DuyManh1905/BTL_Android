package com.duymanh.btl.model;

import java.io.Serializable;

public class Introducer implements Serializable {
    private Integer id;

    private String information;

    public Introducer(int id, String information) {
        this.id = id;
        this.information = information;
    }

    @Override
    public String toString() {
        return "Introducer{" +
                "id=" + id +
                ", information='" + information + '\'' +
                '}';
    }

    public Introducer(String information) {
        this.information = information;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
