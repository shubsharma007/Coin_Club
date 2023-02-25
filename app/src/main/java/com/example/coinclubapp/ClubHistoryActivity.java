package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.coinclubapp.databinding.ActivityClubHistoryBinding;

public class ClubHistoryActivity extends AppCompatActivity {
    ActivityClubHistoryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityClubHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}