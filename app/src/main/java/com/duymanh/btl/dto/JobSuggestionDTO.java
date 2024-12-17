package com.duymanh.btl.dto;

import java.io.Serializable;

public class JobSuggestionDTO implements Serializable {
    private Integer id;

    private String desiredJob;
    private String desiredWorkLocation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesiredJob() {
        return desiredJob;
    }

    public void setDesiredJob(String desiredJob) {
        this.desiredJob = desiredJob;
    }

    public String getDesiredWorkLocation() {
        return desiredWorkLocation;
    }

    public void setDesiredWorkLocation(String desiredWorkLocation) {
        this.desiredWorkLocation = desiredWorkLocation;
    }
}
