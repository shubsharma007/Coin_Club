package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.coinclubapp.databinding.ActivityClubJoiningFormTwoBinding;

public class ClubJoiningFormTwoActivity extends AppCompatActivity {
    ActivityClubJoiningFormTwoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityClubJoiningFormTwoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClubJoiningFormTwoActivity.this,MainActivity.class));
            }
        });
    }
}