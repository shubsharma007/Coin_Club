package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.coinclubapp.databinding.ActivityClubJoiningFormOneBinding;

public class ClubJoiningFormOneActivity extends AppCompatActivity {

    ActivityClubJoiningFormOneBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClubJoiningFormOneBinding.inflate (getLayoutInflater ());
        setContentView(binding.getRoot ());

        String name =  binding.fullNameEt.getText ().toString ().trim ();
        String city = binding.cityEt.getText ().toString ().trim ();
        String organisation = binding.noyoEt.getText ().toString ().trim ();

        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClubJoiningFormOneActivity.this,ClubJoiningFormTwoActivity.class));
            }
        });
    }
}