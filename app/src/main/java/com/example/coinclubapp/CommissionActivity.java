package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.coinclubapp.Adapters.CommissionClubCloseAdapter;
import com.example.coinclubapp.Adapters.CommissionSoForAdapter;
import com.example.coinclubapp.Adapters.CommissionTimeAdapter;
import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Response.CommissionClubCloseResponse;
import com.example.coinclubapp.Response.CommissionSoFarResponse;
import com.example.coinclubapp.Response.CommissionTimeResponse;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityCommissionBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommissionActivity extends AppCompatActivity {
    ActivityCommissionBinding binding;
    Context context;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommissionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.firstRv.setLayoutManager(new LinearLayoutManager(CommissionActivity.this));

        binding.secondRv.setLayoutManager(new LinearLayoutManager(CommissionActivity.this));

        binding.thirdRv.setLayoutManager(new LinearLayoutManager(CommissionActivity.this));

        ApiInterface apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);
        Call<List<CommissionTimeResponse>> call = apiInterface.getCommissionTime();
        call.enqueue(new Callback<List<CommissionTimeResponse>>() {
            @Override
            public void onResponse(Call<List<CommissionTimeResponse>> call, Response<List<CommissionTimeResponse>> response) {
                if (response.isSuccessful()) {
                    binding.firstRv.setAdapter(new CommissionTimeAdapter(response.body()));

                } else {
                    Log.d("Error", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<CommissionTimeResponse>> call, Throwable t) {
                Log.d("Error", t.getMessage());

            }
        });

        Call<List<CommissionSoFarResponse>> call1 = apiInterface.getCommissionSoFor();
        call1.enqueue(new Callback<List<CommissionSoFarResponse>>() {
            @Override
            public void onResponse(Call<List<CommissionSoFarResponse>> call, Response<List<CommissionSoFarResponse>> response) {
                if (response.isSuccessful()) {
                    binding.secondRv.setAdapter(new CommissionSoForAdapter(response.body()));
                } else {
                    Log.d("Error", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<CommissionSoFarResponse>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

        Call<List<CommissionClubCloseResponse>> call2 = apiInterface.getCommissionClubClose();
        call2.enqueue(new Callback<List<CommissionClubCloseResponse>>() {
            @Override
            public void onResponse(Call<List<CommissionClubCloseResponse>> call, Response<List<CommissionClubCloseResponse>> response) {
                if (response.isSuccessful()){
                    binding.thirdRv.setAdapter(new CommissionClubCloseAdapter(response.body()));
                }else {
                    Log.d("Error",response.message());
                }
            }

            @Override
            public void onFailure(Call<List<CommissionClubCloseResponse>> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}