package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.coinclubapp.Adapters.HotClubAdapter;
import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityHotClubBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotClubActivity extends AppCompatActivity {
    ActivityHotClubBinding binding;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHotClubBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.progressBar.setVisibility(View.VISIBLE);

        layoutManager = new LinearLayoutManager(HotClubActivity.this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);


        ApiInterface apiInterface= RetrofitService.getRetrofit().create(ApiInterface.class);
//        Call<ClubResponse> call=apiInterface.getClubs();
//        call.enqueue(new Callback<ClubResponse>() {
//            @Override
//            public void onResponse(Call<ClubResponse> call, Response<ClubResponse> response) {
//                binding.progressBar.setVisibility(View.GONE);
//                if(response.isSuccessful())
//                {
//                    ClubResponse clubResponse=response.body();
//                    binding.recyclerView.setAdapter(new HotClubAdapter(HotClubActivity.this,clubResponse));
//                }
//                else
//                {
//                    Toast.makeText(HotClubActivity.this, "no clubs available", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ClubResponse> call, Throwable t) {
//                Toast.makeText(HotClubActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });


        binding.bottomNavView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.notification:
                    startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));
                    break;
                case R.id.profile:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    break;
                case R.id.money:
                    startActivity(new Intent(getApplicationContext(), MyBankActivity.class));
                    break;
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    break;
            }
            return false;
        });

    }

//    @Override
//    public void onItemClick(int position) {
//
//        Intent intent = new Intent(this, Club_Activity.class);
//        startActivity(intent);
//
//    }
}