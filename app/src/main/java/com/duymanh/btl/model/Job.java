package com.duymanh.btl.model;

import java.io.Serializable;

public class Job implements Serializable {
    private int id;
    private String title;
    private String startAt;
    private String endAt;
    private String desciption;
    private String salary;
    private Company company;
    private CateJob cateJob;
    private JobRequirement jobRequirement;

    // Getters and Setters


    public Job(int id, String title, String startAt, String endAt, String desciption, String salary, Company company, CateJob cateJob, JobRequirement jobRequirement) {
        this.id = id;
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.desciption = desciption;
        this.salary = salary;
        this.company = company;
        this.cateJob = cateJob;
        this.jobRequirement = jobRequirement;
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

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public CateJob getCateJob() {
        return cateJob;
    }

    public void setCateJob(CateJob cateJob) {
        this.cateJob = cateJob;
    }

    public JobRequirement getJobRequirement() {
        return jobRequirement;
    }

    public void setJobRequirement(JobRequirement jobRequirement) {
        this.jobRequirement = jobRequirement;
    }
}