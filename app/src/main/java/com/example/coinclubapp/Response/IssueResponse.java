package com.example.coinclubapp.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IssueResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Issue")
    @Expose
    private String issue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }
}
