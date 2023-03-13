package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.coinclubapp.databinding.ActivityInsideClubHistoryBinding;

public class InsideClubHistoryActivity extends AppCompatActivity {

    ActivityInsideClubHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsideClubHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backBtn.setOnClickListener(v -> {
            finish();
        });
    }
}