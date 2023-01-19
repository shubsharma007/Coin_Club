package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.coinclubapp.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(this, SliderActivity.class));
        }, 2500);
    }
}