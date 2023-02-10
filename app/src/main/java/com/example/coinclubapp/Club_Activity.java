package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.coinclubapp.Adapters.MemberAdapter;
import com.example.coinclubapp.Adapters.RoundAdapter;
import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityClubBinding;

import com.example.coinclubapp.databinding.ActivityClubBinding;
import com.example.coinclubapp.result.RoundsResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Club_Activity extends AppCompatActivity {
    ActivityClubBinding binding;

    RecyclerView.LayoutManager layoutManagerM;
    RecyclerView.LayoutManager layoutManagerR;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClubBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSetting.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SettingActivity.class));
        });
        binding.backBtn.setOnClickListener(v -> {
            Intent i = new Intent(Club_Activity.this, HotClubActivity.class);
            startActivity(i);
            finish();
        });

        binding.recyclerViewMember.setAdapter(new MemberAdapter());
        layoutManagerM = new LinearLayoutManager(Club_Activity.this, LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerViewMember.setLayoutManager(layoutManagerM);


        layoutManagerR = new LinearLayoutManager(Club_Activity.this, LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerViewRound.setLayoutManager(layoutManagerR);

        apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);
        Call<List<RoundsResult>> call = apiInterface.getAllRounds();
        call.enqueue(new Callback<List<RoundsResult>>() {
            @Override
            public void onResponse(Call<List<RoundsResult>> call, Response<List<RoundsResult>> response) {
                if (response.isSuccessful()) {
                    binding.recyclerViewRound.setAdapter(new RoundAdapter(response.body()));
                }
            }


            @Override
            public void onFailure(Call<List<RoundsResult>> call, Throwable t) {

            }
        });

        binding.seeAllMember.setOnClickListener(v -> {
            Intent i = new Intent(Club_Activity.this, MemberActivity.class);
            startActivity(i);
        });
    }
}