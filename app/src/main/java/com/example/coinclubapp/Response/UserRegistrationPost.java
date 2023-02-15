package com.example.coinclubapp.Response;

import com.example.coinclubapp.result.Result;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRegistrationPost {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("result")
    @Expose
    private Result result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}
