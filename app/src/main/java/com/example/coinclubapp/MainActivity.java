package com.example.coinclubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.coinclubapp.databinding.ActivityLoginBinding;
import com.example.coinclubapp.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private long pressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fab.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),HotClubActivity.class)));


        binding.bottomNavView.setOnItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.notification:
                    startActivity(new Intent(getApplicationContext(),NotificationsActivity.class));
                    break;
                case R.id.profile:
                    startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                    break;
                case R.id.money:
                    startActivity(new Intent(getApplicationContext(),MyBankActivity.class));
                    break;
            }
            return false;
        });

    }
    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            finish();
            super.onBackPressed();
            finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}