package com.example.coinclubapp.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IssuesForSpinnerResponseGet {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("issue")
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
