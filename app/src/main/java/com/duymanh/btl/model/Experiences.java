package com.duymanh.btl.model;

import java.io.Serializable;

public class Experiences implements Serializable {
    private Integer id;

    private String position; //vi tri

    private String company;

    private String startAt;

    @Override
    public String toString() {
        return "Experiences{" +
                "id=" + id +
                ", position='" + position + '\'' +
                ", company='" + company + '\'' +
                ", startAt='" + startAt + '\'' +
                ", endAt='" + endAt + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    private String endAt;

    private String description;

    public Experiences(int id, String position, String company, String startAt, String endAt, String description) {
        this.id = id;
        this.position = position;
        this.company = company;
        this.startAt = startAt;
        this.endAt = endAt;
        this.description = description;
    }

    public Experiences(String position, String company, String startAt, String endAt, String description) {
        this.position = position;
        this.company = company;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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
