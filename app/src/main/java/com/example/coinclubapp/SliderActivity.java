package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.coinclubapp.databinding.ActivitySliderBinding;

public class SliderActivity extends AppCompatActivity {
    ActivitySliderBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySliderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.getStartedBtn.setOnClickListener(v -> {
            startActivity(new Intent(SliderActivity.this,LoginActivity.class));
        });
    }
}