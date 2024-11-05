package com.duymanh.btl.api;

import com.duymanh.btl.model.Company;


import java.util.List;

public class ApiResponseCompany {
    private int code;
    private String msg;
    private Data data;

    public class Data {
        private int page;
        private int size;
        private int totalElements;
        private List<Company> contents;

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

        public List<Company> getContents() {
            return contents;
        }

        public void setContents(List<Company> contents) {
            this.contents = contents;
        }
    }

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
}