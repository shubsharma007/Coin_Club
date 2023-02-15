package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;


import com.example.coinclubapp.Adapters.SeeAllMembersAdapter;
import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Response.AllUserProfilesGet;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityMemberBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberActivity extends AppCompatActivity {

    ActivityMemberBinding binding;
    RecyclerView member_list_Rv;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        binding = ActivityMemberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        member_list_Rv = findViewById (R.id.member_list_Rv);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        binding.memberListRv.setLayoutManager(layoutManager);
        apiInterface= RetrofitService.getRetrofit().create(ApiInterface.class);


        Call<List<AllUserProfilesGet>> call=apiInterface.getAllUsers();
        call.enqueue(new Callback<List<AllUserProfilesGet>>() {
            @Override
            public void onResponse(Call<List<AllUserProfilesGet>> call, Response<List<AllUserProfilesGet>> response) {
                if(response.isSuccessful())
                {
                    binding.memberListRv.setAdapter(new SeeAllMembersAdapter(MemberActivity.this,response.body()));
                }else
                {
                    Toast.makeText(MemberActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<AllUserProfilesGet>> call, Throwable t) {
                Toast.makeText(MemberActivity.this, "Try again", Toast.LENGTH_SHORT).show();
            }
        });


        binding.backBtn.setOnClickListener(v -> {
            finish();
        });
    }
}