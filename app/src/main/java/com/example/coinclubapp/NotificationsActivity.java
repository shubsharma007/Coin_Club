package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.coinclubapp.Adapters.NotificationAdapter;
import com.example.coinclubapp.databinding.ActivityNotificationsBinding;

public class NotificationsActivity extends AppCompatActivity {
    ActivityNotificationsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNotificationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new NotificationAdapter());
        binding.btnSetting.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SettingActivity.class));
        });
        binding.backBtn.setOnClickListener (v -> {
            finish ();
        });
    }
}