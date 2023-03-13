package com.example.coinclubapp.Response;

public class CommissionTimeResponse {

    private int id;
    private String trasfertime;
    private String commission;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrasfertime() {
        return trasfertime;
    }

    public void setTrasfertime(String trasfertime) {
        this.trasfertime = trasfertime;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }
}
