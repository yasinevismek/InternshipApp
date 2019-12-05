package com.example.nazmi.mobilexstaj.model;

public class UsefulDefaultModel {

    public String linkTitle;
    public String linkHref;

    public UsefulDefaultModel() {
    }

    public UsefulDefaultModel(String linkTitle, String linkHref) {
        this.linkTitle = linkTitle;
        this.linkHref = linkHref;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public String getLinkHref() {
        return linkHref;
    }

    public void setLinkHref(String linkHref) {
        this.linkHref = linkHref;
    }
}
