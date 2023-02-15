package com.example.coinclubapp.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KycResult {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("registeruser")
    @Expose
    private Integer registeruser;
    @SerializedName("fullname")
    @Expose
    private Object fullname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("aadharno")
    @Expose
    private String aadharno;
    @SerializedName("aadharfrontimg")
    @Expose
    private String aadharfrontimg;
    @SerializedName("aadharbackimg")
    @Expose
    private String aadharbackimg;
    @SerializedName("panno")
    @Expose
    private String panno;
    @SerializedName("panimg")
    @Expose
    private String panimg;
    @SerializedName("is_verified")
    @Expose
    private Boolean isVerified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRegisteruser() {
        return registeruser;
    }

    public void setRegisteruser(Integer registeruser) {
        this.registeruser = registeruser;
    }

    public Object getFullname() {
        return fullname;
    }

    public void setFullname(Object fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAadharno() {
        return aadharno;
    }

    public void setAadharno(String aadharno) {
        this.aadharno = aadharno;
    }

    public String getAadharfrontimg() {
        return aadharfrontimg;
    }

    public void setAadharfrontimg(String aadharfrontimg) {
        this.aadharfrontimg = aadharfrontimg;
    }

    public String getAadharbackimg() {
        return aadharbackimg;
    }

    public void setAadharbackimg(String aadharbackimg) {
        this.aadharbackimg = aadharbackimg;
    }

    public String getPanno() {
        return panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }

    public String getPanimg() {
        return panimg;
    }

    public void setPanimg(String panimg) {
        this.panimg = panimg;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }
}
