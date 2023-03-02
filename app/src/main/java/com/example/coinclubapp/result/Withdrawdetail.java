package com.example.coinclubapp.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Withdrawdetail {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("userwallet")
    @Expose
    private Integer userwallet;
    @SerializedName("withdrawamount")
    @Expose
    private String withdrawamount;
    @SerializedName("withdraw_date")
    @Expose
    private String withdrawDate;
    @SerializedName("withdraw_time")
    @Expose
    private String withdrawTime;
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

    public String getWithdrawamount() {
        return withdrawamount;
    }

    public void setWithdrawamount(String withdrawamount) {
        this.withdrawamount = withdrawamount;
    }

    public String getWithdrawDate() {
        return withdrawDate;
    }

    public void setWithdrawDate(String withdrawDate) {
        this.withdrawDate = withdrawDate;
    }

    public String getWithdrawTime() {
        return withdrawTime;
    }

    public void setWithdrawTime(String withdrawTime) {
        this.withdrawTime = withdrawTime;
    }

    public Boolean getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
