package com.example.coinclubapp.Response;

import java.util.List;

public class Response {
    private boolean status;
    private String status_code;
    private String message;
//    private FormTwoResult result;
    private List<Object> error;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
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

//    public FormTwoResult getResult() {
//        return result;
//    }
//
//    public void setResult(FormTwoResult result) {
//        this.result = result;
//    }

    public List<Object> getError() {
        return error;
    }

    public void setError(List<Object> error) {
        this.error = error;
    }
}
