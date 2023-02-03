package com.example.coinclubapp.Response;

import com.example.coinclubapp.result.LoginResult;

public class LoginResponse {


    private Boolean status;
    private String statusCode;
    private String message;
    private LoginResult login_data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginResult getLogin_data() {
        return login_data;
    }

    public void setLogin_data(LoginResult login_data) {
        this.login_data = login_data;
    }
}
