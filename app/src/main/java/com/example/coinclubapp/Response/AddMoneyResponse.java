package com.example.coinclubapp.Response;

import com.example.coinclubapp.result.Adddetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddMoneyResponse {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("adddetail")
    @Expose
    private Adddetail adddetail;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Adddetail getAdddetail() {
        return adddetail;
    }

    public void setAdddetail(Adddetail adddetail) {
        this.adddetail = adddetail;
    }
}
