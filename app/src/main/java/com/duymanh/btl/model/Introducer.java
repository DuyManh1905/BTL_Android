package com.duymanh.btl.model;

import java.io.Serializable;

public class Introducer implements Serializable {
    private int id;

    private String information;

    public Introducer(int id, String information) {
        this.id = id;
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
