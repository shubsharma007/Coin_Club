package com.example.coinclubapp.Response;

import com.example.coinclubapp.result.RegisterUser;

public class KycResponse {
    private int id;
    private RegisterUser registeruser;
    private String address;
    private String mobile;
    private String aadharno;
    private String aadharfrontimg;
    private String aadharbackimg;
    private String panno;
    private String panimg;
    private boolean is_verified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RegisterUser getRegisteruser() {
        return registeruser;
    }

    public void setRegisteruser(RegisterUser registeruser) {
        this.registeruser = registeruser;
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

    public boolean isIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }
}
