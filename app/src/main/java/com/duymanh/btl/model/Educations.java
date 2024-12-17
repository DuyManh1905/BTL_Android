package com.duymanh.btl.model;

import java.io.Serializable;

public class Educations implements Serializable {

    private int id;

    private String major;

    private String school;

    private String startAt;

    private String endAt;

    private String description;

    public Educations(int id, String major, String school, String startAt, String endAt, String description) {
        this.id = id;
        this.major = major;
        this.school = school;
        this.startAt = startAt;
        this.endAt = endAt;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
