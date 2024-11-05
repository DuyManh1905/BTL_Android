package com.duymanh.btl.dto;

import java.io.Serializable;
import java.util.Date;

public class ApplicationFormDTO implements Serializable {
    private Integer id;

    private Date createAt;

    private JobDTO job;

    private UserDTO user;

    // Constructor không tham số
    public ApplicationFormDTO() {}

    // Constructor với các tham số
    public ApplicationFormDTO(Integer id, Date createAt, JobDTO job, UserDTO user) {
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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public JobDTO getJob() {
        return job;
    }

    public void setJob(JobDTO job) {
        this.job = job;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
