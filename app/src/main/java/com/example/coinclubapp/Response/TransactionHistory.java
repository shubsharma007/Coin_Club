package com.example.coinclubapp.Response;

import com.example.coinclubapp.result.Transc;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransactionHistory {

    @SerializedName("transcuser")
    @Expose
    private String transcuser;
    @SerializedName("transc")
    @Expose
    private List<Transc> transc;

    public String getTranscuser() {
        return transcuser;
    }

    public void setTranscuser(String transcuser) {
        this.transcuser = transcuser;
    }

    public List<Transc> getTransc() {
        return transc;
    }

    public void setTransc(List<Transc> transc) {
        this.transc = transc;
    }


}
