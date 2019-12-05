package com.example.nazmi.mobilexstaj.model;

public class BlogModel {
    public String blogTitle;
    public String blogContent;
    public String blogLink;
    public String blogTime;
    public String blogUser;

    public BlogModel() {
    }

    public BlogModel(String blogTitle, String blogContent, String blogLink, String blogTime, String blogUser) {
        this.blogTitle = blogTitle;
        this.blogContent = blogContent;
        this.blogLink = blogLink;
        this.blogTime = blogTime;
        this.blogUser = blogUser;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }

    public String getBlogLink() {
        return blogLink;
    }

    public void setBlogLink(String blogLink) {
        this.blogLink = blogLink;
    }

    public String getBlogTime() {
        return blogTime;
    }

    public void setBlogTime(String blogTime) {
        this.blogTime = blogTime;
    }

    public String getBlogUser() {
        return blogUser;
    }

    public void setBlogUser(String blogUser) {
        this.blogUser = blogUser;
    }
}
