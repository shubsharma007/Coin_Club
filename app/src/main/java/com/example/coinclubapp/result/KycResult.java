package com.example.coinclubapp.result;

public class KycResult {

    private int id;
    private String full_name;
    private String mobileno;
    private String aadharno;
    private String aadharfrontimg;
    private String aadharbackimg;
    private String panno;
    private String panfrontimg;
    private String panbackimg;
    private boolean is_verified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
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

    public String getPanfrontimg() {
        return panfrontimg;
    }

    public void setPanfrontimg(String panfrontimg) {
        this.panfrontimg = panfrontimg;
    }

    public String getPanbackimg() {
        return panbackimg;
    }

    public void setPanbackimg(String panbackimg) {
        this.panbackimg = panbackimg;
    }

    public boolean isIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }
}
