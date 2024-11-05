package com.duymanh.btl.model;

import java.io.Serializable;

public class Company implements Serializable {
    private int id;
    private String name;
    private String address;
    private String desciption;
    private String size;

    private String stype;

    private String linkWebsite;
    private String avataURL;
    private String imageCoverURL;

    // Getters and Setters


    public Company(int id, String name, String address, String desciption, String size, String stype, String linkWebsite, String avataURL, String imageCoverURL) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.desciption = desciption;
        this.size = size;
        this.stype = stype;
        this.linkWebsite = linkWebsite;
        this.avataURL = avataURL;
        this.imageCoverURL = imageCoverURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getLinkWebsite() {
        return linkWebsite;
    }

    public void setLinkWebsite(String linkWebsite) {
        this.linkWebsite = linkWebsite;
    }

    public String getAvataURL() {
        return avataURL;
    }

    public void setAvataURL(String avataURL) {
        this.avataURL = avataURL;
    }

    public String getImageCoverURL() {
        return imageCoverURL;
    }

    public void setImageCoverURL(String imageCoverURL) {
        this.imageCoverURL = imageCoverURL;
    }
}