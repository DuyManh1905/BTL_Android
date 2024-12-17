package com.duymanh.btl.model;

import java.io.Serializable;

public class Profile implements Serializable {
    private int id;

    private String name;

    private String gender;	//gioi tinh

    private String dateBirth;

    private String phoneNumber;

    private String address;

    private String email;

    private String website;

    private String avatarBase64;

    public Profile(int id, String name, String gender, String dateBirth, String phoneNumber, String address, String email, String website, String avatarBase64) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.dateBirth = dateBirth;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.website = website;
        this.avatarBase64 = avatarBase64;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAvatarBase64() {
        return avatarBase64;
    }

    public void setAvatarBase64(String avatarBase64) {
        this.avatarBase64 = avatarBase64;
    }
}
