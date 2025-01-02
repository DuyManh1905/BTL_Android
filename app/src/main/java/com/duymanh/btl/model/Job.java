package com.duymanh.btl.model;

import java.io.Serializable;

public class Job implements Serializable {
    private int id;
    private String title;
    private String startAt;
    private String endAt;
    private String desciption;
    private String form;
    //so luong tuyen
    private int numberRecruitment;
    //cap bac
    private String ranking;

    //dia diem lam viec
    private String workLocation;

    //thoi gian lam viec
    private String time;
    private String salary;
    private Company company;
    private CateJob cateJob;
    private JobRequirement jobRequirement;

    //quyen loi
    private String interest;

    // Getters and Setters


    public Job() {
    }

    public Job(int id, String title, String startAt, String endAt, String desciption, String form, int numberRecruitment, String ranking, String workLocation, String time, String salary, Company company, CateJob cateJob, JobRequirement jobRequirement, String interest) {
        this.id = id;
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.desciption = desciption;
        this.form = form;
        this.numberRecruitment = numberRecruitment;
        this.ranking = ranking;
        this.workLocation = workLocation;
        this.time = time;
        this.salary = salary;
        this.company = company;
        this.cateJob = cateJob;
        this.jobRequirement = jobRequirement;
        this.interest = interest;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
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

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public int getNumberRecruitment() {
        return numberRecruitment;
    }

    public void setNumberRecruitment(int numberRecruitment) {
        this.numberRecruitment = numberRecruitment;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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