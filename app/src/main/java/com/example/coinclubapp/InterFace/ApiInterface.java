package com.example.coinclubapp.InterFace;

import com.example.coinclubapp.Response.Response;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("user-register")
    Call<Response> postItems(@Field("mobile") String mobile,
                             @Field("password") String password,
                             @Field("full_name") String full_name,
                             @Field("gender") String gender,
                             @Field("city") String city,
                             @Field("occupation") String occupation,
                             @Field("organization") String organization,
                             @Field("deposite") String deposite,
                             @Field("motive") String motive,
                             @Field("income") String income);
}
