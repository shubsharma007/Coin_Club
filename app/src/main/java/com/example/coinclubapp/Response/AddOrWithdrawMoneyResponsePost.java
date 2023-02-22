package com.example.coinclubapp.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddOrWithdrawMoneyResponsePost {
    @SerializedName("userwallet")
    @Expose
    private Integer userwallet;
    @SerializedName("walletamount")
    @Expose
    private String walletamount;
    @SerializedName("totalamount")
    @Expose
    private String totalamount;
    @SerializedName("walletimg")
    @Expose
    private String walletimg;
    @SerializedName("walletwithdraw")
    @Expose
    private String walletwithdraw;
    @SerializedName("is_added")
    @Expose
    private Boolean isAdded;
    @SerializedName("is_withdraw")
    @Expose
    private Boolean isWithdraw;

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

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getWalletimg() {
        return walletimg;
    }

    public void setWalletimg(String walletimg) {
        this.walletimg = walletimg;
    }

    public String getWalletwithdraw() {
        return walletwithdraw;
    }

    public void setWalletwithdraw(String walletwithdraw) {
        this.walletwithdraw = walletwithdraw;
    }

    public Boolean getIsAdded() {
        return isAdded;
    }

    public void setIsAdded(Boolean isAdded) {
        this.isAdded = isAdded;
    }

    public Boolean getIsWithdraw() {
        return isWithdraw;
    }

    public void setIsWithdraw(Boolean isWithdraw) {
        this.isWithdraw = isWithdraw;
    }
}
