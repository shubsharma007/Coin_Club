package com.example.coinclubapp.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Roundpayment {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("clubname")
    @Expose
    private String clubname;
    @SerializedName("roundno")
    @Expose
    private String roundno;
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
    @SerializedName("startdate")
    @Expose
    private String startdate;
    @SerializedName("starttime")
    @Expose
    private String starttime;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("is_completed")
    @Expose
    private Boolean isCompleted;
    @SerializedName("Payment_status")
    @Expose
    private Boolean paymentStatus;

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

    public String getRoundno() {
        return roundno;
    }

    public void setRoundno(String roundno) {
        this.roundno = roundno;
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

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Boolean getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}
