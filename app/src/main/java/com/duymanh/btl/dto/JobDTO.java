package com.duymanh.btl.dto;

import java.io.Serializable;

public class JobDTO implements Serializable {

    private Integer id;
    private String jobTitle;

    // Constructor không tham số
    public JobDTO() {}

    // Constructor với tham số
    public JobDTO(Integer id, String jobTitle) {
        this.id = id;
        this.jobTitle = jobTitle;
    }

    // Getter và Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
