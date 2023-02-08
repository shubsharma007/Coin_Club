package com.example.coinclubapp.InterFace;

import com.example.coinclubapp.Response.KycResponse;
import com.example.coinclubapp.result.BankDetailsResult;
import com.example.coinclubapp.result.LoginResult;
import com.example.coinclubapp.result.FormTwoResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("regs_list/")
    Call<FormTwoResult> registerNewUser(@Field("full_name") String full_name,
                                        @Field("mobileno") String mobileno,
                                        @Field("city") String city,
                                        @Field("password") String password,
                                        @Field("gender") String gender,
                                        @Field("occupation") String occupation,
                                        @Field("motive") String motive,
                                        @Field("income") String income,
                                        @Field("monthlycontribution") String monthlycontribution,
                                        @Field("profileimg") String profileimg,
                                        @Field("email") String email
    );


    @FormUrlEncoded
    @POST("bslogin/")
    Call<LoginResult> UserLogin(@Field("mobileno") String mobileno,
                                @Field("password") String password);


    @FormUrlEncoded
    @POST("userkyc/")
    Call<KycResponse> PostKycItems(
            @Field("id") String id,
            @Field("address") String address,
            @Field("mobile") String mobile,
            @Field("aadharno") String aadharno,
            @Field("aadharfrontimg") String aadharfrontimg,
            @Field("aadharbackimg") String aadharbackimg,
            @Field("panno") String panno,
            @Field("panimg") String panimg
    );



    @FormUrlEncoded
    @POST("bankdetails/")
    Call<BankDetailsResult> postBankDetails(@Field("registerno")String registerno,
                                            @Field("IFSCcode") String IFSCcode,
                                            @Field("accountname")String accountname,
                                            @Field("accountnumber") String accountnumber,
                                            @Field("passbookimg") String passbookimg);

//    @GET("club-data-get")
//    Call<ClubResponse> getClubs();


    @GET("userkyc/")
    Call<List<KycResponse>> getKycItem();

    @GET("userkyc/{id}")
    Call<KycResponse> getKycById(@Path("id") int id);


}
