package com.example.coinclubapp.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Adddetail {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("userwallet")
    @Expose
    private Integer userwallet;
    @SerializedName("walletamount")
    @Expose
    private String walletamount;
    @SerializedName("walletimg")
    @Expose
    private String walletimg;
    @SerializedName("add_date")
    @Expose
    private String addDate;
    @SerializedName("add_time")
    @Expose
    private String addTime;
    @SerializedName("payment_status")
    @Expose
    private Boolean paymentStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserwallet() {
        return userwallet;
    }

    public void setUserwallet(Integer userwallet) {
        this.userwallet = userwallet;
    }

    public String getWalletamount() {
        return walletamount;
    }

    public void setWalletamount(String walletamount) {
        this.walletamount = walletamount;
    }

    public String getWalletimg() {
        return walletimg;
    }

    public void setWalletimg(String walletimg) {
        this.walletimg = walletimg;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public Boolean getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
