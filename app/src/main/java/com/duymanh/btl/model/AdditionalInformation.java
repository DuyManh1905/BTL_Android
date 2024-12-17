package com.duymanh.btl.model;

import java.io.Serializable;

public class AdditionalInformation implements Serializable {
    private int id;

    private String addInfor;

    public AdditionalInformation(int id, String addInfor) {
        this.id = id;
        this.addInfor = addInfor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddInfor() {
        return addInfor;
    }

    public void setAddInfor(String addInfor) {
        this.addInfor = addInfor;
    }
}
