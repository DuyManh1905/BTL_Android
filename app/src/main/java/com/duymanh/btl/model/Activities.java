package com.duymanh.btl.model;

import java.io.Serializable;

public class Activities implements Serializable {
    private int id;

    private String position;	//vi tri chuc vu

    private String organization;	//co quan

    private String startAt;

    private String endAt;

    private String description;

    public Activities(int id, String position, String organization, String startAt, String endAt, String description) {
        this.id = id;
        this.position = position;
        this.organization = organization;
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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
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
