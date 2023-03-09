package com.example.coinclubapp.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Looser {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("mobileno")
    @Expose
    private String mobileno;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("motive")
    @Expose
    private String motive;
    @SerializedName("income")
    @Expose
    private String income;
    @SerializedName("wallet_amount")
    @Expose
    private String walletAmount;
    @SerializedName("monthlycontribution")
    @Expose
    private String monthlycontribution;
    @SerializedName("profileimg")
    @Expose
    private String profileimg;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("alternateno")
    @Expose
    private String alternateno;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;
    @SerializedName("create_at")
    @Expose
    private String createAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getMotive() {
        return motive;
    }

    public void setMotive(String motive) {
        this.motive = motive;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(String walletAmount) {
        this.walletAmount = walletAmount;
    }

    public String getMonthlycontribution() {
        return monthlycontribution;
    }

    public void setMonthlycontribution(String monthlycontribution) {
        this.monthlycontribution = monthlycontribution;
    }

    public String getProfileimg() {
        return profileimg;
    }

    public void setProfileimg(String profileimg) {
        this.profileimg = profileimg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlternateno() {
        return alternateno;
    }

    public void setAlternateno(String alternateno) {
        this.alternateno = alternateno;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
