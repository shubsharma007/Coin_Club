package com.example.coinclubapp.Response;

import android.telephony.mbms.MbmsErrors;

import com.example.coinclubapp.result.ClubResult;

import java.util.List;

public class ClubResponse {

    private Boolean status;
    private String status_code;
    private String message;
    private List<ClubResult> club;
    private List<Object> errors;
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setClub(List<ClubResult> club) {
        this.club = club;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getStatus_code() {
        return status_code;
    }

    public String getMessage() {
        return message;
    }

    public List<ClubResult> getClub() {
        return club;
    }

    public List<Object> getErrors() {
        return errors;
    }
}
