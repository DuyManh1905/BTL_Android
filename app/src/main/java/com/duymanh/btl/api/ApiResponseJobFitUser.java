package com.duymanh.btl.api;

import com.duymanh.btl.model.Job;

import java.util.List;

public class ApiResponseJobFitUser {
    private int code;
    private String msg;
    private List<Job> data;

    // Getters and Setters

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Job> getData() {
        return data;
    }

    public void setData(List<Job> data) {
        this.data = data;
    }
}
