package com.example.coinclubapp.Response;

import com.example.coinclubapp.result.KycResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KycResponse {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("kycresult")
    @Expose
    private KycResult kycresult;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public KycResult getKycresult() {
        return kycresult;
    }

    public void setKycresult(KycResult kycresult) {
        this.kycresult = kycresult;
    }
}
