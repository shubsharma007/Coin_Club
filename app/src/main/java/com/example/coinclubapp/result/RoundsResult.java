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
    @SerializedName("roundno")
    @Expose
    private String roundno;
    @SerializedName("roundname")
    @Expose
    private String roundname;
    @SerializedName("minbid")
    @Expose
    private Object minbid;
    @SerializedName("maxbid")
    @Expose
    private Object maxbid;
    @SerializedName("winner")
    @Expose
    private String winner;
    @SerializedName("roundamount")
    @Expose
    private String roundamount;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("startdate")
    @Expose
    private Object startdate;
    @SerializedName("starttime")
    @Expose
    private Object starttime;
    @SerializedName("duration")
    @Expose
    private Object duration;
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

    public Object getMinbid() {
        return minbid;
    }

    public void setMinbid(Object minbid) {
        this.minbid = minbid;
    }

    public Object getMaxbid() {
        return maxbid;
    }

    public void setMaxbid(Object maxbid) {
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

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getStartdate() {
        return startdate;
    }

    public void setStartdate(Object startdate) {
        this.startdate = startdate;
    }

    public Object getStarttime() {
        return starttime;
    }

    public void setStarttime(Object starttime) {
        this.starttime = starttime;
    }

    public Object getDuration() {
        return duration;
    }

    public void setDuration(Object duration) {
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
