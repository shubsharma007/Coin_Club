package com.example.coinclubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.coinclubapp.Adapters.HotClubAdapter;
import com.example.coinclubapp.InterFace.HotClubRecyclerView;
import com.example.coinclubapp.databinding.ActivityHotClubBinding;
import com.example.coinclubapp.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class HotClubActivity extends AppCompatActivity implements HotClubRecyclerView {
    ActivityHotClubBinding binding;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHotClubBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        layoutManager = new LinearLayoutManager(HotClubActivity.this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(new HotClubAdapter(this, this));


        binding.fab.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), HotClubActivity.class)));
        binding.bottomNavView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.notification:
                    startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));
                    break;
                case R.id.profile:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    break;
                case R.id.money:
                    startActivity(new Intent(getApplicationContext(), MyBankActivity.class));
                    break;
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    break;
            }
            return false;
        });

    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(this, Club_Activity.class);
        startActivity(intent);

    }
}