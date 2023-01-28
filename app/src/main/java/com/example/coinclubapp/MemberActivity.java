package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.coinclubapp.databinding.ActivityMemberBinding;

public class MemberActivity extends AppCompatActivity {

    ActivityMemberBinding binding;
    RecyclerView member_list_Rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        binding = ActivityMemberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        member_list_Rv = findViewById (R.id.member_list_Rv);

        binding.backBtn.setOnClickListener(v -> {
            finish();
        });
    }
}