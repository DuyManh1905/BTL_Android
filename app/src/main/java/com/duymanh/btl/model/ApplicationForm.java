package com.duymanh.btl.model;

import com.duymanh.btl.dto.JobDTO;
import com.duymanh.btl.dto.UserDTO;

import java.io.Serializable;
import java.util.Date;

public class ApplicationForm implements Serializable {
    private Integer id;

    private String createAt;

    private Job job;

    private User user;

    private int status;

    // Constructor không tham số
    public ApplicationForm() {}

    // Constructor với các tham số
    public ApplicationForm(Integer id, String createAt, Job job, User user) {
        this.id = id;
        this.createAt = createAt;
        this.job = job;
        this.user = user;
    }

    // Getter và Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
