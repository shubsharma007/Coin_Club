package com.example.coinclubapp.Response;

public class CommissionSoFarResponse {
    private int id;
    private String account_tran;
    private String commission;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount_tran() {
        return account_tran;
    }

    public void setAccount_tran(String account_tran) {
        this.account_tran = account_tran;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }
}
