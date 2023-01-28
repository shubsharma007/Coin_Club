package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.coinclubapp.Adapters.MemberAdapter;
import com.example.coinclubapp.Adapters.RoundAdapter;
import com.example.coinclubapp.databinding.ActivityClubBinding;

import com.example.coinclubapp.databinding.ActivityClubBinding;

public class Club_Activity extends AppCompatActivity {
    ActivityClubBinding binding;

    RecyclerView.LayoutManager layoutManagerM;
    RecyclerView.LayoutManager layoutManagerR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClubBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(v -> {
            finish();
        });
        binding=ActivityClubBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerViewMember.setAdapter(new MemberAdapter());
        layoutManagerM=new LinearLayoutManager(Club_Activity.this,LinearLayoutManager.HORIZONTAL,false);
        binding.recyclerViewMember.setLayoutManager(layoutManagerM);


        binding.recyclerViewRound.setAdapter(new RoundAdapter());
        layoutManagerR=new LinearLayoutManager(Club_Activity.this,LinearLayoutManager.HORIZONTAL,false);
        binding.recyclerViewRound.setLayoutManager(layoutManagerR);

        binding.seeAllMember.setOnClickListener (v -> {
            Intent i = new Intent (Club_Activity.this, MemberActivity.class);
            startActivity (i);
        });
    }
}