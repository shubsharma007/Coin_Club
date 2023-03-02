package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.coinclubapp.Adapters.LosersAdapter;
import com.example.coinclubapp.databinding.ActivityWinnerLoserBinding;

public class WinnerLoserActivity extends AppCompatActivity {
ActivityWinnerLoserBinding binding;
String winnerName,winnerAmount,winnerImage,clubName,roundNumber;
int winnerId,Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityWinnerLoserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        Id = sharedPreferences.getInt("Id", 0);

        winnerName=getIntent().getStringExtra("winnerName");
        winnerAmount=getIntent().getStringExtra("winnerAmount");
        winnerImage=getIntent().getStringExtra("winnerImage");
        winnerId=getIntent().getIntExtra("winnerId",0);
        clubName=getIntent().getStringExtra("clubName");
        roundNumber=getIntent().getStringExtra("roundNumber");

       if(Id==winnerId)
       {
           binding.cardView.setVisibility(View.GONE);
           binding.recyclerView.setVisibility(View.VISIBLE);

           binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
           binding.recyclerView.setAdapter(new LosersAdapter());
       }
       else
       {
           binding.cardView.setVisibility(View.VISIBLE);
           binding.recyclerView.setVisibility(View.GONE);
           Glide.with(WinnerLoserActivity.this).load(winnerImage).placeholder(R.drawable.avatar).into(binding.dpImg);
           binding.winnerName.setText(winnerName);
           binding.winnerId.setText(String.valueOf(winnerId));
           binding.winnerAmount.setText(winnerAmount);
           binding.clubName.setText(clubName);
           binding.roundNumber.setError(roundNumber);

           binding.payBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   startActivity(new Intent(WinnerLoserActivity.this,MyBankActivity.class));
               }
           });
       }

    }
}