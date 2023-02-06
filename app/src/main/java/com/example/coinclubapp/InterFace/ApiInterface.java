package com.example.coinclubapp.InterFace;

import com.example.coinclubapp.Response.ClubResponse;
import com.example.coinclubapp.Response.KycResponse;
import com.example.coinclubapp.result.LoginResult;
import com.example.coinclubapp.result.Result;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("regs_list/")
    Call<Result> postItems(@Field("mobileno") String mobileno,
                           @Field("password") String password,
                           @Field("full_name") String full_name,
                           @Field("gender") String gender,
                           @Field("city") String city,
                           @Field("occupation") String occupation,
                           @Field("organisation") String organisation,
                           @Field("monthlycontribution") String monthlycontribution,
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

    @GET("club-data-get")
    Call<ClubResponse> getClubs();


    @FormUrlEncoded
    @POST("bslogin/")
    Call<LoginResult> UserLogin(@Field("mobileno") String mobileno,
                                @Field("password") String password);
}
