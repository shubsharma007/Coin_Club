package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if(sharedPreferences.getAll().containsKey("number") && sharedPreferences.getAll().containsKey("Id"))
            {
                startActivity(new Intent(this, MainActivity.class));
            }
            else
            {
                startActivity(new Intent(this, LoginActivity.class));
            }
        }, 2500);
    }
}