package com.example.coinclubapp.InterFace;

import android.view.ViewDebug;

import com.example.coinclubapp.Response.KycResponse;
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

    @FormUrlEncoded
    @POST("user-update")
    Call<KycResponse> PostKycItems(
            @Field("id") String id,
            @Field("full_name") String full_name,
            @Field("kyc_mobile") String kyc_mobile,
            @Field("address") String address,
            @Field("id_name") String id_name,
            @Field("id_number") String id_number,
            @Field("email_id") String email_id,
            @Field("id_frontimg") String id_frontimg,
            @Field("id_back_img") String id_back_img,
            @Field("driving_licence_no") String driving_licence_no,
            @Field("licence_expiry_date") String licence_expiry_date
    );
}
