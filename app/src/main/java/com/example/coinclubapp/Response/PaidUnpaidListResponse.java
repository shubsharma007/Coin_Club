package com.example.coinclubapp.Response;

import com.example.coinclubapp.result.Looser;
import com.example.coinclubapp.result.Roundpayment;
import com.example.coinclubapp.result.Winner;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaidUnpaidListResponse {
    @SerializedName("roundpayment")
    @Expose
    private Roundpayment roundpayment;
    @SerializedName("winner")
    @Expose
    private Winner winner;
    @SerializedName("looser")
    @Expose
    private Looser looser;
    @SerializedName("payamount")
    @Expose
    private String payamount;
    @SerializedName("is_paid")
    @Expose
    private Boolean isPaid;

    public Roundpayment getRoundpayment() {
        return roundpayment;
    }

    public void setRoundpayment(Roundpayment roundpayment) {
        this.roundpayment = roundpayment;
    }

    public Winner getWinner() {
        return winner;
    }

    public void setWinner(Winner winner) {
        this.winner = winner;
    }

    public Looser getLooser() {
        return looser;
    }

    public void setLooser(Looser looser) {
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
