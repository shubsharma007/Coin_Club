package com.example.coinclubapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coinclubapp.Fragments.ChangePinFragment;
import com.example.coinclubapp.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {
    ActivitySettingBinding binding;
    int Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        Id = sharedPreferences.getInt("Id", 0);


        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
        binding.cpTv.setOnClickListener (v -> {
            ChangePinFragment changePinFragment = new ChangePinFragment ();
            Bundle bundle = new Bundle();
            bundle.putInt("Id", Id);

            changePinFragment.setArguments(bundle);
            changePinFragment.show(getSupportFragmentManager(),changePinFragment.getTag());
        });
        binding.uKycTv.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), KycDetailsActivity.class));
        });
        binding.tncTv.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), TermsAndConditionActivity.class));
        });
        binding.ppTv.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Privacy_PolicyActivity.class));
        });
    }
}