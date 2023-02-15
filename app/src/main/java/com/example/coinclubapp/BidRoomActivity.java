package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coinclubapp.Adapters.BidsAdapter;
import com.example.coinclubapp.Fragments.BidNowFragment;
import com.example.coinclubapp.databinding.ActivityBidRoomBinding;

public class BidRoomActivity extends AppCompatActivity {
    ActivityBidRoomBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBidRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BidNowFragment bidNowFragment =new BidNowFragment();
        binding.bidBtn.setOnClickListener(v -> {


            bidNowFragment.show(getSupportFragmentManager(),bidNowFragment.getTag());

            View sheetViews= LayoutInflater.from(getApplicationContext()).inflate(R.layout.fragment_bid_now,null);
            EditText et=sheetViews.findViewById(R.id.bidAmtEt);
            AppCompatButton acb=sheetViews.findViewById(R.id.myBidButton);

            acb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(BidRoomActivity.this, et.getText().toString() + "₹", Toast.LENGTH_SHORT).show();
                    bidNowFragment.dismiss();
                }
            });

        });

        binding.bidRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.bidRecyclerView.setAdapter(new BidsAdapter());
    }
}