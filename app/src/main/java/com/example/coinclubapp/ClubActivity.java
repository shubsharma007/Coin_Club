package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;


import com.example.coinclubapp.Adapters.MemberAdapter;
import com.example.coinclubapp.Adapters.RoundAdapter;
import com.example.coinclubapp.Adapters.SeeAllMembersAdapter;
import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Response.AllClubsGet;
import com.example.coinclubapp.Response.AllUserProfilesGet;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityClubBinding;

import com.example.coinclubapp.result.RoundsResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubActivity extends AppCompatActivity {
    ActivityClubBinding binding;

    RecyclerView.LayoutManager layoutManagerM;
    RecyclerView.LayoutManager layoutManagerR;

    ApiInterface apiInterface;
    String startDATE, startTIME;
     int roundId;
    String duration;
    String useTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClubBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);
        binding.bidStartIn.setEnabled(false);
        Intent ii = getIntent();
        binding.clubName.setText(getIntent().getStringExtra("clubName"));



        String clubId = ii.getStringExtra("id");

        Call<AllClubsGet> callClubs = apiInterface.getClubById(clubId);
        callClubs.enqueue(new Callback<AllClubsGet>() {
            @Override
            public void onResponse(Call<AllClubsGet> call, Response<AllClubsGet> response) {
                if (response.isSuccessful()) {
                    binding.clubAmountTv.setText(response.body().getClubamount());
                    binding.perHeadTv.setText(response.body().getClubcontribution());
                    binding.nextRoundTv.setText(response.body().getStartdate());

                } else {
                    Toast.makeText(ClubActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AllClubsGet> call, Throwable t) {
                Toast.makeText(ClubActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.bidStartIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.bidStartIn.isEnabled()) {
                    Intent i = new Intent(ClubActivity.this, BidRoomActivity.class);
                    i.putExtra("roundId",roundId);
                    i.putExtra("duration",duration);
                    i.putExtra("startDate",startDATE);
                    i.putExtra("startTime",startTIME);
                    startActivity(i);
                } else {
                    Toast.makeText(ClubActivity.this, "Button Is Disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.backBtn.setOnClickListener(v -> {
            Intent i = new Intent(ClubActivity.this, HotClubActivity.class);
            startActivity(i);
            finish();
        });

        layoutManagerM = new LinearLayoutManager(ClubActivity.this, LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerViewMember.setLayoutManager(layoutManagerM);


        layoutManagerR = new LinearLayoutManager(ClubActivity.this, LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerViewRound.setLayoutManager(layoutManagerR);

        Call<List<AllUserProfilesGet>> callM = apiInterface.getAllUsers();
        callM.enqueue(new Callback<List<AllUserProfilesGet>>() {
            @Override
            public void onResponse(Call<List<AllUserProfilesGet>> call, Response<List<AllUserProfilesGet>> response) {
                if (response.isSuccessful()) {
                    binding.recyclerViewMember.setAdapter(new MemberAdapter(ClubActivity.this, response.body()));
                } else {
                    Toast.makeText(ClubActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<AllUserProfilesGet>> call, Throwable t) {
                Toast.makeText(ClubActivity.this, "Try again", Toast.LENGTH_SHORT).show();
            }
        });


        Call<List<RoundsResult>> callR = apiInterface.getAllRounds();
        callR.enqueue(new Callback<List<RoundsResult>>() {
            @Override
            public void onResponse(Call<List<RoundsResult>> call, Response<List<RoundsResult>> response) {
                if (response.isSuccessful()) {

                    List<RoundsResult> myRounds=new ArrayList<>();
                    for(int i=0;i<response.body().size();i++)
                    {
                        if(Objects.equals(ii.getStringExtra("clubName"), response.body().get(i).getClubname()))
                        {
                            myRounds.add(response.body().get(i));
                        }

                    }
                    binding.recyclerViewRound.setAdapter(new RoundAdapter(myRounds));
                    for(int i=0;i<myRounds.size();i++)
                    {
                        RoundsResult roundJiskiBidStartHogi=new RoundsResult();
                        if(!myRounds.get(i).getIsCompleted())
                        {
                             roundJiskiBidStartHogi=myRounds.get(i);
                            startDATE = String.valueOf(roundJiskiBidStartHogi.getStartdate());
                            startTIME = String.valueOf(roundJiskiBidStartHogi.getStarttime());
                            useTime = startDATE + " " + startTIME;
                            duration = String.valueOf(roundJiskiBidStartHogi.getDuration());
                            roundId=roundJiskiBidStartHogi.getId();
                            try {
                                countDownFunc(useTime);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            break;
                        }


                    }

                }
            }


            @Override
            public void onFailure(Call<List<RoundsResult>> call, Throwable t) {
                Toast.makeText(ClubActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.seeAllMember.setOnClickListener(v -> {
            Intent i = new Intent(ClubActivity.this, MemberActivity.class);
            startActivity(i);
        });
    }

    private void countDownFunc(String mydate) throws ParseException {

        if (mydate != null) {
            String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

            Handler handler = new Handler();

            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            Date event_date = dateFormat.parse(mydate);
            Date current_date = new Date();
            if (event_date.getTime() - current_date.getTime() > 0) {
                Runnable runnable;
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            handler.postDelayed(this, 1000);
                            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                            Date event_date = dateFormat.parse(mydate);
                            Date current_date = new Date();
                            if (!current_date.after(event_date)) {
                                long diff = event_date.getTime() - current_date.getTime();
                                long Days = diff / (24 * 60 * 60 * 1000);
                                long Hours = diff / (60 * 60 * 1000) % 24;
                                long Minutes = diff / (60 * 1000) % 60;
                                long Seconds = diff / 1000 % 60;

                                binding.bidStartIn.setText("");
                                binding.bidStartIn.setEnabled(false);
                                binding.bidStartIn.setAllCaps(false);
                                binding.bidStartIn.setText(String.format("%02d", Days) + " days," + String.format("%02d", Hours) + " hrs," + String.format("%02d", Minutes) + " min," + String.format("%02d", Seconds) + " sec left");


                            } else {
                                handler.removeCallbacks(null);

//                                    binding.bidStartIn.setText("Start Bidding");
//
//                                    binding.bidStartIn.setEnabled(true);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                handler.postDelayed(runnable, 0);
            } else {

                binding.bidStartIn.setText("Start Bidding");

                binding.bidStartIn.setTextColor(Color.WHITE);
                binding.bidStartIn.setBackgroundResource(R.drawable.start_bidding_bg);
                binding.bidStartIn.setEnabled(true);
            }

        } else {
            binding.bidStartIn.setText("Start Bidding");

            binding.bidStartIn.setTextColor(Color.WHITE);
            binding.bidStartIn.setBackgroundResource(R.drawable.start_bidding_bg);
            binding.bidStartIn.setEnabled(true);
        }
    }
}
