package com.duymanh.btl.model;

import java.io.Serializable;
import java.util.List;

public class Cv implements Serializable {
    private Integer id;
    private String name;
    private String description;
    private int isPublic;
    private String createAt;
    private String updateCreatAt;
    //cong viec mong muon
    private String desiredJob;
    //dia diem lam viec mong muon
    private String desiredWorkLocation;
    private User user;

    private List<Activities> activities;

    private List<AdditionalInformation> additionalInformations;

    private List<Awards> awards;

    private List<BusinessCard> businessCards;

    private List<Certificates> certificates;

    private List<Educations> educations;

    private List<Experiences> experiences;

    private List<Hobbies> hobbies;

    private List<Introducer> introducers;

    private Profile profile;

    private List<Skills> skills;

    private List<Project> projects;

    public Cv() {
    }

    public Cv(int id, String name, String description, int isPublic, String createAt, String updateCreatAt, String desiredJob, String desiredWorkLocation, User user, List<Activities> activities, List<AdditionalInformation> additionalInformations, List<Awards> awards, List<BusinessCard> businessCards, List<Certificates> certificates, List<Educations> educations, List<Experiences> experiences, List<Hobbies> hobbies, List<Introducer> introducers, Profile profile, List<Skills> skills, List<Project> projects) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isPublic = isPublic;
        this.createAt = createAt;
        this.updateCreatAt = updateCreatAt;
        this.desiredJob = desiredJob;
        this.desiredWorkLocation = desiredWorkLocation;
        this.user = user;
        this.activities = activities;
        this.additionalInformations = additionalInformations;
        this.awards = awards;
        this.businessCards = businessCards;
        this.certificates = certificates;
        this.educations = educations;
        this.experiences = experiences;
        this.hobbies = hobbies;
        this.introducers = introducers;
        this.profile = profile;
        this.skills = skills;
        this.projects = projects;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateCreatAt() {
        return updateCreatAt;
    }

    public void setUpdateCreatAt(String updateCreatAt) {
        this.updateCreatAt = updateCreatAt;
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

    public List<Activities> getActivities() {
        return activities;
    }

    public void setActivities(List<Activities> activities) {
        this.activities = activities;
    }

    public List<AdditionalInformation> getAdditionalInformations() {
        return additionalInformations;
    }

    public void setAdditionalInformations(List<AdditionalInformation> additionalInformations) {
        this.additionalInformations = additionalInformations;
    }

    public List<Awards> getAwards() {
        return awards;
    }

    public void setAwards(List<Awards> awards) {
        this.awards = awards;
    }

    public List<BusinessCard> getBusinessCards() {
        return businessCards;
    }

    public void setBusinessCards(List<BusinessCard> businessCards) {
        this.businessCards = businessCards;
    }

    public List<Certificates> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Certificates> certificates) {
        this.certificates = certificates;
    }

    public List<Educations> getEducations() {
        return educations;
    }

    public void setEducations(List<Educations> educations) {
        this.educations = educations;
    }

    public List<Experiences> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experiences> experiences) {
        this.experiences = experiences;
    }

    public List<Hobbies> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<Hobbies> hobbies) {
        this.hobbies = hobbies;
    }

    public List<Introducer> getIntroducers() {
        return introducers;
    }

    public void setIntroducers(List<Introducer> introducers) {
        this.introducers = introducers;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Skills> getSkills() {
        return skills;
    }

    public void setSkills(List<Skills> skills) {
        this.skills = skills;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Cv{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isPublic=" + isPublic +
                ", createAt='" + createAt + '\'' +
                ", updateCreatAt='" + updateCreatAt + '\'' +
                ", desiredJob='" + desiredJob + '\'' +
                ", desiredWorkLocation='" + desiredWorkLocation + '\'' +
                ", activities=" + activities +
                ", additionalInformations=" + additionalInformations +
                ", awards=" + awards +
                ", businessCards=" + businessCards +
                ", certificates=" + certificates +
                ", educations=" + educations +
                ", experiences=" + experiences +
                ", hobbies=" + hobbies +
                ", introducers=" + introducers +
                ", profile=" + profile +
                ", skills=" + skills +
                ", projects=" + projects +
                '}';
    }
}
