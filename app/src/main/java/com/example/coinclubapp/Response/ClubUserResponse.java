package com.example.coinclubapp.Response;

import com.example.coinclubapp.result.Clubuser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClubUserResponse {

    @SerializedName("club")
    @Expose
    private String club;
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
    private String isCompleted;
    @SerializedName("clubuser")
    @Expose
    private List<Clubuser> clubuser;

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
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

    public String getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted;
    }

    public List<Clubuser> getClubuser() {
        return clubuser;
    }

    public void setClubuser(List<Clubuser> clubuser) {
        this.clubuser = clubuser;
    }

}
