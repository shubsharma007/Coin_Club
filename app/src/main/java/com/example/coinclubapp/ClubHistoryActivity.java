package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.coinclubapp.Adapters.ClubHistoryAdapter;
import com.example.coinclubapp.Adapters.MyClubsAdapter;
import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Response.UserClubResponse;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityClubHistoryBinding;
import com.example.coinclubapp.result.Userclub;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubHistoryActivity extends AppCompatActivity {
    ActivityClubHistoryBinding binding;
    Context context;
    ApiInterface apiInterface;
    int Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityClubHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);

        Id = sharedPreferences.getInt("Id", 0);
//        binding.clubContainer.setLayoutManager(new LinearLayoutManager(context));
//        binding.clubContainer.setAdapter(new ClubHistoryAdapter(context));

        binding.clubContainer.setLayoutManager(new LinearLayoutManager(ClubHistoryActivity.this));
//        binding.progressBar.setVisibility(View.VISIBLE);


        Call<List<UserClubResponse>> call = apiInterface.getClubsByUserId(Id);
        call.enqueue(new Callback<List<UserClubResponse>>() {
            @Override
            public void onResponse(Call<List<UserClubResponse>> call, Response<List<UserClubResponse>> response) {
                if (response.isSuccessful()) {
                    UserClubResponse resp = response.body().get(0);

                    binding.clubContainer.setAdapter(new ClubHistoryAdapter(ClubHistoryActivity.this, resp.getUserclub()));
//                    binding.progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(context, "some error occured", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserClubResponse>> call, Throwable t) {
                Toast.makeText(context, "Failure,Try Again", Toast.LENGTH_SHORT).show();
            }
        });


    }
}