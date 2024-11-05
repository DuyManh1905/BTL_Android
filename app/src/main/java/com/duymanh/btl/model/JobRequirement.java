package com.duymanh.btl.model;

import java.io.Serializable;

public class JobRequirement implements Serializable {
    private Integer id;
    private String experience;
    private String major;
    private String area;
    private String programLanguage;
    private String languageCertificate;
    private String skillRequire;

    public JobRequirement(Integer id, String experience, String major, String area, String programLanguage, String languageCertificate, String skillRequire) {
        this.id = id;
        this.experience = experience;
        this.major = major;
        this.area = area;
        this.programLanguage = programLanguage;
        this.languageCertificate = languageCertificate;
        this.skillRequire = skillRequire;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProgramLanguage() {
        return programLanguage;
    }

    public void setProgramLanguage(String programLanguage) {
        this.programLanguage = programLanguage;
    }

    public String getLanguageCertificate() {
        return languageCertificate;
    }

    public void setLanguageCertificate(String languageCertificate) {
        this.languageCertificate = languageCertificate;
    }

    public String getSkillRequire() {
        return skillRequire;
    }

    public void setSkillRequire(String skillRequire) {
        this.skillRequire = skillRequire;
    }
}
