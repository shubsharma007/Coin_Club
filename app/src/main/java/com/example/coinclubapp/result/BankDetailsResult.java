package com.example.coinclubapp.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankDetailsResult {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("registerno")
    @Expose
    private String registerno;
    @SerializedName("IFSCcode")
    @Expose
    private String iFSCcode;
    @SerializedName("accountname")
    @Expose
    private String accountname;
    @SerializedName("accountnumber")
    @Expose
    private String accountnumber;
    @SerializedName("passbookimg")
    @Expose
    private String passbookimg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegisterno() {
        return registerno;
    }

    public void setRegisterno(String registerno) {
        this.registerno = registerno;
    }

    public String getIFSCcode() {
        return iFSCcode;
    }

    public void setIFSCcode(String iFSCcode) {
        this.iFSCcode = iFSCcode;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public String getPassbookimg() {
        return passbookimg;
    }

    public void setPassbookimg(String passbookimg) {
        this.passbookimg = passbookimg;
    }

}
