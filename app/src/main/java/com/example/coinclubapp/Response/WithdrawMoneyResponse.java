package com.example.coinclubapp.Response;

import com.example.coinclubapp.result.Withdrawdetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WithdrawMoneyResponse {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("withdrawdetail")
    @Expose
    private Withdrawdetail withdrawdetail;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Withdrawdetail getWithdrawdetail() {
        return withdrawdetail;
    }

    public void setWithdrawdetail(Withdrawdetail withdrawdetail) {
        this.withdrawdetail = withdrawdetail;
    }
}
