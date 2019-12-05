package com.example.nazmi.mobilexstaj.model;

/**
 * Created by Nazmican GÖKBULUT
 */

//Todo: FireBase User Information Object Reference
public class User {
    //Todo: Identify User Object
    public String userName, email, schoolName, sectionName, internDate, socialInfo;

    //Todo: User to Empty Constructor
    public User() {
    }

    //Todo: User to Constructor
    public User(String userName, String email, String schoolName, String sectionName, String internDate, String socialInfo) {
        this.userName = userName;
        this.email = email;
        this.schoolName = schoolName;
        this.sectionName = sectionName;
        this.internDate = internDate;
        this.socialInfo = socialInfo;
    }

    //Todo: Wind for Getter Setter Methods

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getInternDate() {
        return internDate;
    }

    public void setInternDate(String internDate) {
        this.internDate = internDate;
    }

    public String getSocialInfo() {
        return socialInfo;
    }

    public void setSocialInfo(String socialInfo) {
        this.socialInfo = socialInfo;
    }

}

