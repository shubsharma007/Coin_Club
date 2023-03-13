package com.example.coinclubapp.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transc {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("roundpayment")
    @Expose
    private String roundpayment;
    @SerializedName("winner")
    @Expose
    private String winner;
    @SerializedName("winnerid")
    @Expose
    private Integer winnerid;
    @SerializedName("looser")
    @Expose
    private String looser;
    @SerializedName("looserid")
    @Expose
    private Integer looserid;
    @SerializedName("payamount")
    @Expose
    private String payamount;
    @SerializedName("is_paid")
    @Expose
    private String isPaid;
    @SerializedName("payment_time")
    @Expose
    private String paymentTime;
    @SerializedName("Category")
    @Expose
    private String category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoundpayment() {
        return roundpayment;
    }

    public void setRoundpayment(String roundpayment) {
        this.roundpayment = roundpayment;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Integer getWinnerid() {
        return winnerid;
    }

    public void setWinnerid(Integer winnerid) {
        this.winnerid = winnerid;
    }

    public String getLooser() {
        return looser;
    }

    public void setLooser(String looser) {
        this.looser = looser;
    }

    public Integer getLooserid() {
        return looserid;
    }

    public void setLooserid(Integer looserid) {
        this.looserid = looserid;
    }

    public String getPayamount() {
        return payamount;
    }

    public void setPayamount(String payamount) {
        this.payamount = payamount;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
