package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.example.coinclubapp.Adapters.MemberAdapter;
import com.example.coinclubapp.Adapters.SeeAllMembersAdapter;
import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Response.AllUserProfilesGet;
import com.example.coinclubapp.Response.ClubUserResponse;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityMemberBinding;
import com.example.coinclubapp.result.Clubuser;

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

        int clubId=getIntent().getIntExtra("clubId",0);
        Call<List<ClubUserResponse>> call=apiInterface.getUsersByClubId(clubId);
        call.enqueue(new Callback<List<ClubUserResponse>>() {
            @Override
            public void onResponse(Call<List<ClubUserResponse>> call, Response<List<ClubUserResponse>> response) {
                if(response.isSuccessful())
                {
                    List<Clubuser> users=response.body().get(0).getClubuser();
                    binding.memberListRv.setAdapter(new SeeAllMembersAdapter(MemberActivity.this,users));
                }
                else
                {
                    Toast.makeText(MemberActivity.this, response.message() + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ClubUserResponse>> call, Throwable t) {
                Toast.makeText(MemberActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
            }
        });



//        Call<List<ClubUserResponse>> call=apiInterface.getUsersByClubId(clubId);
//        call.enqueue(new Callback<List<ClubUserResponse>>() {
//            @Override
//            public void onResponse(Call<List<ClubUserResponse>> call, Response<List<ClubUserResponse>> response) {
//                if (response.isSuccessful()) {
//                    List<Clubuser> clubusers=response.body().get(0).getClubuser();
//                    binding.memberListRv.setAdapter(new SeeAllMembersAdapter(MemberActivity.this,clubusers));
//
//                } else {
//                    Log.d("fsdfsdf",response.raw().body().toString());
//                    Toast.makeText(MemberActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<ClubUserResponse>> call, Throwable t) {
//                Toast.makeText(MemberActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
//
//            }
//        });

        binding.backBtn.setOnClickListener(v -> {
            finish();
        });
    }
}