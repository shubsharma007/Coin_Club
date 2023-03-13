package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.coinclubapp.Adapters.MemberAdapter;
import com.example.coinclubapp.Adapters.OldMembersAdapter;
import com.example.coinclubapp.Adapters.OldRoundsAdapter;
import com.example.coinclubapp.Adapters.RoundAdapter;
import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Response.AllClubsGet;
import com.example.coinclubapp.Response.ClubUserResponse;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityInsideClubHistoryBinding;
import com.example.coinclubapp.result.Clubuser;
import com.example.coinclubapp.result.RoundsResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class InsideClubHistoryActivity extends AppCompatActivity {

    ActivityInsideClubHistoryBinding binding;
    int clubId;
    String clubName;
    ApiInterface apiInterface;
    RecyclerView.LayoutManager layoutManagerMembers;
    RecyclerView.LayoutManager layoutManagerRounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsideClubHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiInterface= RetrofitService.getRetrofit().create(ApiInterface.class);

        clubName=getIntent().getStringExtra("clubName");
        clubId=getIntent().getIntExtra("clubId",0);

        binding.clubName.setText(clubName);


        layoutManagerMembers = new LinearLayoutManager(InsideClubHistoryActivity.this, LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerViewMember.setLayoutManager(layoutManagerMembers);
        Call<List<ClubUserResponse>> membersCall=apiInterface.getUsersByClubId(clubId);
        membersCall.enqueue(new Callback<List<ClubUserResponse>>() {
            @Override
            public void onResponse(Call<List<ClubUserResponse>> call, Response<List<ClubUserResponse>> response) {
                if(response.isSuccessful())
                {
                    List<Clubuser> clubusers = response.body().get(0).getClubuser();
                    binding.recyclerViewMember.setAdapter(new OldMembersAdapter(InsideClubHistoryActivity.this, clubusers));
                }
                else
                {
                    Toast.makeText(InsideClubHistoryActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    Log.d("ERROR2",response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ClubUserResponse>> call, Throwable t) {
                Toast.makeText(InsideClubHistoryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ERROR1",t.getMessage());
            }
        });

        layoutManagerRounds = new LinearLayoutManager(InsideClubHistoryActivity.this, LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerViewRound.setLayoutManager(layoutManagerRounds);
        Call<List<RoundsResult>> callRounds = apiInterface.getAllRounds();
        callRounds.enqueue(new Callback<List<RoundsResult>>() {
            @Override
            public void onResponse(Call<List<RoundsResult>> call, Response<List<RoundsResult>> response) {
                if(response.isSuccessful())
                {
                    List<RoundsResult> myRounds = new ArrayList<>();

                    // to get list of all rounds of this club
                    for (int i = 0; i < response.body().size(); i++) {
                        if (Objects.equals(getIntent().getStringExtra("clubName"), response.body().get(i).getClubname())) {
                            myRounds.add(response.body().get(i));
                        }
                    }

                    binding.recyclerViewRound.setAdapter(new OldRoundsAdapter(myRounds, InsideClubHistoryActivity.this));
                }
                else
                {
                    Toast.makeText(InsideClubHistoryActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    Log.d("ERROR3",response.message());
                }
            }

            @Override
            public void onFailure(Call<List<RoundsResult>> call, Throwable t) {
                Toast.makeText(InsideClubHistoryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        binding.backBtn.setOnClickListener(v -> {
            finish();
        });
    }
}