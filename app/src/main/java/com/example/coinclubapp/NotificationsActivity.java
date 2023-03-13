package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.coinclubapp.Adapters.NotificationAdapter;
import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Response.NotificationListModel;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityNotificationsBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsActivity extends AppCompatActivity {
    ActivityNotificationsBinding binding;
    ApiInterface apiInterface;

    int Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        Id = sharedPreferences.getInt("Id", 0);


        apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);
        Call<List<NotificationListModel>> call =apiInterface.getAllNotifications();
        call.enqueue(new Callback<List<NotificationListModel>>() {
            @Override
            public void onResponse(Call<List<NotificationListModel>> call, Response<List<NotificationListModel>> response) {
                if(response.isSuccessful())
                {
                    binding.noDataAvailableTv.setVisibility(View.GONE);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(NotificationsActivity.this);
                    layoutManager.setReverseLayout(true);
                    layoutManager.setStackFromEnd(true);
                    binding.recyclerView.setLayoutManager(layoutManager);

                    List<NotificationListModel> myNotifications=new ArrayList<>();
                    List<NotificationListModel> allNotifications=response.body();
                    for(NotificationListModel temp:allNotifications)
                    {
                        if(temp.getUsernotification()==Id)
                        {
                            myNotifications.add(temp);
                        }
                    }
                    binding.recyclerView.setAdapter(new NotificationAdapter(myNotifications,NotificationsActivity.this));
                    binding.recyclerView.setVisibility(View.VISIBLE);
                }
                else
                {
                    binding.noDataAvailableTv.setVisibility(View.VISIBLE);
                    binding.recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<NotificationListModel>> call, Throwable t) {
                binding.noDataAvailableTv.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
            }
        });


        binding.backBtn.setOnClickListener(v -> {
            finish();
        });
    }
}