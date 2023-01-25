package com.example.coinclubapp.Response;

import com.example.coinclubapp.result.KycResult;

import java.util.List;

public class KycResponse {
    private Boolean status;
    private String status_code;
    private String message;
    private KycResult kyc_result;
    private List<Object> error;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public KycResult getKyc_result() {
        return kyc_result;
    }

    public void setKyc_result(KycResult kyc_result) {
        this.kyc_result = kyc_result;
    }

    public List<Object> getError() {
        return error;
    }

    public void setError(List<Object> error) {
        this.error = error;
    }
}
