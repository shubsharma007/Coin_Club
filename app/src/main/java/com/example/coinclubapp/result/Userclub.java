package com.example.coinclubapp.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Userclub {
    @SerializedName("club")
    @Expose
    private String club;
    @SerializedName("clubid")
    @Expose
    private Integer clubid;
    @SerializedName("clubname")
    @Expose
    private String clubname;
    @SerializedName("clubimage")
    @Expose
    private String clubimage;
    @SerializedName("clubamount")
    @Expose
    private String clubamount;
    @SerializedName("clubmembers")
    @Expose
    private String clubmembers;
    @SerializedName("clubcontribution")
    @Expose
    private String clubcontribution;
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
    private boolean isCompleted;

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public Integer getClubid() {
        return clubid;
    }

    public void setClubid(Integer clubid) {
        this.clubid = clubid;
    }

    public String getClubname() {
        return clubname;
    }

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }

    public String getClubimage() {
        return clubimage;
    }

    public void setClubimage(String clubimage) {
        this.clubimage = clubimage;
    }

    public String getClubamount() {
        return clubamount;
    }

    public void setClubamount(String clubamount) {
        this.clubamount = clubamount;
    }

    public String getClubmembers() {
        return clubmembers;
    }

    public void setClubmembers(String clubmembers) {
        this.clubmembers = clubmembers;
    }

    public String getClubcontribution() {
        return clubcontribution;
    }

    public void setClubcontribution(String clubcontribution) {
        this.clubcontribution = clubcontribution;
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

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}
