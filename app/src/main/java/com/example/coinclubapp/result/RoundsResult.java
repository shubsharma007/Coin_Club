package com.example.coinclubapp.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoundsResult {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("clubname")
    @Expose
    private String clubname;
    @SerializedName("roundname")
    @Expose
    private String roundname;
    @SerializedName("minbid")
    @Expose
    private String minbid;
    @SerializedName("maxbid")
    @Expose
    private String maxbid;
    @SerializedName("winner")
    @Expose
    private String winner;
    @SerializedName("roundamount")
    @Expose
    private String roundamount;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("is_completed")
    @Expose
    private String isCompleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClubname() {
        return clubname;
    }

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }

    public String getRoundname() {
        return roundname;
    }

    public void setRoundname(String roundname) {
        this.roundname = roundname;
    }

    public String getMinbid() {
        return minbid;
    }

    public void setMinbid(String minbid) {
        this.minbid = minbid;
    }

    public String getMaxbid() {
        return maxbid;
    }

    public void setMaxbid(String maxbid) {
        this.maxbid = maxbid;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getRoundamount() {
        return roundamount;
    }

    public void setRoundamount(String roundamount) {
        this.roundamount = roundamount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted;
    }
}
