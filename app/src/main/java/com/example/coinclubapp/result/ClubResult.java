package com.example.coinclubapp.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClubResult {
    @SerializedName("id")
    @Expose
    private Integer id;
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
    private Integer clubmembers;
    @SerializedName("clubcontribution")
    @Expose
    private String clubcontribution;
    @SerializedName("startdate")
    @Expose
    private String startdate;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("is_completed")
    @Expose
    private Boolean isCompleted;

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

    public Integer getClubmembers() {
        return clubmembers;
    }

    public void setClubmembers(Integer clubmembers) {
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

}
