package com.duymanh.btl.model;

import java.io.Serializable;

public class Project implements Serializable {
    private Integer id;

    private String name;

    private String customer;

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", customer='" + customer + '\'' +
                ", teamSize='" + teamSize + '\'' +
                ", position='" + position + '\'' +
                ", technologies='" + technologies + '\'' +
                ", startAt='" + startAt + '\'' +
                ", endAt='" + endAt + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    private String teamSize;

    private String position;

    private String technologies;

    private String startAt;

    private String endAt;

    private String description;

    public Project(int id, String name, String customer, String teamSize, String position, String technologies, String startAt, String endAt, String description) {
        this.id = id;
        this.name = name;
        this.customer = customer;
        this.teamSize = teamSize;
        this.position = position;
        this.technologies = technologies;
        this.startAt = startAt;
        this.endAt = endAt;
        this.description = description;
    }

    public Project(String name, String customer, String teamSize, String position, String technologies, String startAt, String endAt, String description) {
        this.name = name;
        this.customer = customer;
        this.teamSize = teamSize;
        this.position = position;
        this.technologies = technologies;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(String teamSize) {
        this.teamSize = teamSize;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
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
