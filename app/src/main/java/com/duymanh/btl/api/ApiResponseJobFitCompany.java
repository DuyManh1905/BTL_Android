package com.duymanh.btl.api;

import com.duymanh.btl.model.Job;

import java.util.List;

public class ApiResponseJobFitCompany {
    private int code;
    private String msg;
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    // Lớp Data để ánh xạ trường `data` trong JSON
    public static class Data {
        private int page;
        private int size;
        private int totalElements;
        private List<Job> contents;

        // Getters and Setters

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public List<Job> getContents() {
            return contents;
        }

        public void setContents(List<Job> contents) {
            this.contents = contents;
        }
    }
}
