package com.example.coinclubapp.BiddingModel;

public class Bidders {
    private int Id;
    private String name;
    private Integer biddingAmount;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBiddingAmount() {
        return biddingAmount;
    }

    public void setBiddingAmount(Integer biddingAmount) {
        this.biddingAmount = biddingAmount;
    }

    public Bidders() {
    }

    public Bidders(int id, String name, Integer biddingAmount) {
        Id = id;
        this.name = name;
        this.biddingAmount = biddingAmount;
    }
}
