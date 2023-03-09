package com.example.coinclubapp.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListToGetIdOfRecord {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("roundpayment")
    @Expose
    private Integer roundpayment;
    @SerializedName("winner")
    @Expose
    private Integer winner;
    @SerializedName("looser")
    @Expose
    private Integer looser;
    @SerializedName("payamount")
    @Expose
    private String payamount;
    @SerializedName("is_paid")
    @Expose
    private Boolean isPaid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoundpayment() {
        return roundpayment;
    }

    public void setRoundpayment(Integer roundpayment) {
        this.roundpayment = roundpayment;
    }

    public Integer getWinner() {
        return winner;
    }

    public void setWinner(Integer winner) {
        this.winner = winner;
    }

    public Integer getLooser() {
        return looser;
    }

    public void setLooser(Integer looser) {
        this.looser = looser;
    }

    public String getPayamount() {
        return payamount;
    }

    public void setPayamount(String payamount) {
        this.payamount = payamount;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

}
