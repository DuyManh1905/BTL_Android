package com.duymanh.btl.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private Integer id;

    private String avataURL;
    private String name;
    private String username;
    private String email;
    private String birthdate;
    private List<Role> roles;

    private List<Cv> cvs;

    public User() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAvataURL() {
        return avataURL;
    }

    public void setAvataURL(String avataURL) {
        this.avataURL = avataURL;
    }

    public User(Integer id, String avataURL, String name, String username, String email, String birthdate, List<Role> roles, List<Cv> cvs) {
        this.id = id;
        this.avataURL = avataURL;
        this.name = name;
        this.username = username;
        this.email = email;
        this.birthdate = birthdate;
        this.roles = roles;
        this.cvs = cvs;
    }

    public List<Cv> getCvs() {
        return cvs;
    }

    public void setCvs(List<Cv> cvs) {
        this.cvs = cvs;
    }

    // Getters and Setters

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}