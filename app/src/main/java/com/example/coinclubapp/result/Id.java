package com.example.coinclubapp.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Id {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("registeruser")
    @Expose
    private Integer registeruser;
    @SerializedName("googlepay_number")
    @Expose
    private String googlepayNumber;
    @SerializedName("paytm_number")
    @Expose
    private String paytmNumber;
    @SerializedName("phonepe_number")
    @Expose
    private String phonepeNumber;
    @SerializedName("bhim_upi")
    @Expose
    private String bhimUpi;
    @SerializedName("document_image")
    @Expose
    private String documentImage;

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

    public String getGooglepayNumber() {
        return googlepayNumber;
    }

    public void setGooglepayNumber(String googlepayNumber) {
        this.googlepayNumber = googlepayNumber;
    }

    public String getPaytmNumber() {
        return paytmNumber;
    }

    public void setPaytmNumber(String paytmNumber) {
        this.paytmNumber = paytmNumber;
    }

    public String getPhonepeNumber() {
        return phonepeNumber;
    }

    public void setPhonepeNumber(String phonepeNumber) {
        this.phonepeNumber = phonepeNumber;
    }

    public String getBhimUpi() {
        return bhimUpi;
    }

    public void setBhimUpi(String bhimUpi) {
        this.bhimUpi = bhimUpi;
    }

    public String getDocumentImage() {
        return documentImage;
    }

    public void setDocumentImage(String documentImage) {
        this.documentImage = documentImage;
    }

}
