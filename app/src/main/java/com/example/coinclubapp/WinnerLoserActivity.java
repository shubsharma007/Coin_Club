package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.coinclubapp.Adapters.LosersAdapter;
import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Response.ProfileResponse;
import com.example.coinclubapp.Response.WinnerPatchToRound;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityWinnerLoserBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public class WinnerLoserActivity extends AppCompatActivity {
    ActivityWinnerLoserBinding binding;
    String winnerName, winnerAmount, winnerImage, clubName, roundNumber;
    int winnerId, Id, roundId;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWinnerLoserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final ProgressDialog progressDialog = new ProgressDialog(WinnerLoserActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        Id = sharedPreferences.getInt("Id", 0);
        winnerName = sharedPreferences.getString("winnerName", "not available");
        winnerAmount = sharedPreferences.getString("winnerAmount", "not available");
        winnerImage = sharedPreferences.getString("winnerImage", "not available");
        winnerId = sharedPreferences.getInt("winnerId", 0);
        clubName = sharedPreferences.getString("clubName", "not available");
        roundNumber = sharedPreferences.getString("roundNumber", "not available");
        roundId = sharedPreferences.getInt("currentRoundId", 0);

        Glide.with(WinnerLoserActivity.this).load("http://meetjob.techpanda.art" + (winnerImage)).placeholder(R.drawable.avatar).into(binding.dpImg);
        binding.winnerName.setText(winnerName);
        binding.winnerId.setText(String.valueOf(winnerId));
        binding.winnerAmount.setText(winnerAmount);
        binding.clubName.setText(clubName);
        binding.roundNumber.setText(roundNumber);

        progressDialog.dismiss();

        if (Id == winnerId) {
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(WinnerLoserActivity.this));
            binding.recyclerView.setAdapter(new LosersAdapter());
            binding.cardView.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);

            // call kro winner set krne wali api ko

        } else {

            binding.cardView.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
            progressDialog.dismiss();
            binding.payBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(WinnerLoserActivity.this, MyBankActivity.class));
                }
            });

        }
    }
}