package com.example.coinclubapp.Response;

import com.example.coinclubapp.result.Issue;

public class CustomerResponse {
    private int id;
    private String discription;
    private Boolean status;
    private Object user;
    private Object issue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

    public Object getIssue() {
        return issue;
    }

    public void setIssue(Object issue) {
        this.issue = issue;
    }
}
