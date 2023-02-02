package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.coinclubapp.databinding.ActivityTermsAndConditionBinding;

public class TermsAndConditionActivity extends AppCompatActivity {
ActivityTermsAndConditionBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityTermsAndConditionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}