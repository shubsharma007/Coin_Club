package com.example.coinclubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import android.view.View;

import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.coinclubapp.Adapters.MyAdapter;
import com.example.coinclubapp.BiddingModel.Bidders;
import com.example.coinclubapp.Fragments.BidNowFragment;
import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Response.ProfileResponse;
import com.example.coinclubapp.Response.RoundCompletedPatchResponse;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityBidRoomBinding;
import com.example.coinclubapp.result.RoundsResult;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;

public class BidRoomActivity extends AppCompatActivity {
    ActivityBidRoomBinding binding;
    private final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    static long diff, Hours, Minutes, Seconds;
    int currentRoundId;
    Handler handler = new Handler();
    Runnable runnable;
    BidNowFragment bidNowFragment;
    FragmentManager fragmentManager;
    ApiInterface apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);
    String roundName, clubName;

    LinearLayoutManager linearLayoutManager;
    MyAdapter myAdapter;
    Bidders bidders;
    List<Bidders> listBidders;
    List<Integer> maxBidderAmount;
    List<Integer> maxBidderId;
    int maxAmt;
    int maxId;

    Dialog adDialog;
    Call<ProfileResponse> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBidRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bidNowFragment = new BidNowFragment();
        linearLayoutManager = new LinearLayoutManager(this);
        maxBidderAmount = new ArrayList<>();
        maxBidderId = new ArrayList<>();
        adDialog = new Dialog(BidRoomActivity.this);

        binding.bidRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        listBidders = new ArrayList<>();


        binding.payBtn.setOnClickListener(v -> {
            showPopup();
        });

        fragmentManager = getSupportFragmentManager();

        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        int Id = sharedPreferences.getInt("Id", 0);

        currentRoundId = getIntent().getIntExtra("roundId", 0);

        Call<RoundsResult> call = apiInterface.getRoundsById(currentRoundId);
        call.enqueue(new Callback<RoundsResult>() {
            @Override
            public void onResponse(Call<RoundsResult> call, Response<RoundsResult> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String startDate = String.valueOf(response.body().getStartdate());
                    String startTime = String.valueOf(response.body().getStarttime());
                    String duration = String.valueOf(response.body().getDuration());
                    roundName = response.body().getRoundname();
                    clubName = response.body().getClubname();


                    try {
                        countdownFunc(startDate, startTime, duration);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.i("hlfuidfhsd", response.message());
                }
            }

            @Override
            public void onFailure(Call<RoundsResult> call, Throwable t) {
                Log.i("bjhbfdjhf", t.getMessage());
            }
        });


        binding.bidBtn.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("Id", Id);
            bundle.putString("clubName", clubName);
            bundle.putString("roundName", roundName);
            bidNowFragment.setArguments(bundle);
            bidNowFragment.show(getSupportFragmentManager(), bidNowFragment.getTag());
        });

        FirebaseRecyclerOptions<Bidders> options =
                new FirebaseRecyclerOptions.Builder<Bidders>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("hello").child("hello round 1").orderByChild("biddingAmount"), Bidders.class)
                        .build();
// yogesh change change

        myAdapter = new MyAdapter(options, BidRoomActivity.this);

        Log.i("fefddffd", roundName + clubName);

        binding.bidRecyclerView.setAdapter(myAdapter);

        DatabaseReference myBidders = FirebaseDatabase.getInstance().getReference("hello").child("hello round 1");

        myBidders.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBidders.clear();
                maxBidderId.clear();
                maxBidderAmount.clear();
                for (DataSnapshot shot : snapshot.getChildren()) {
                    bidders = shot.getValue(Bidders.class);
                    listBidders.add(bidders);
                    maxBidderId.add(bidders.getId());
                    maxBidderAmount.add(bidders.getBiddingAmount());
                    int maxAmt = maxBidderAmount.get(0);
                    int maxId = maxBidderId.get(0);

                    for (int i = 0; i < maxBidderAmount.size(); i++) {
                        if (maxBidderAmount.get(i) > maxAmt) {
                            maxAmt = maxBidderAmount.get(i);
                            maxId = maxBidderId.get(i);
                        }
                    }
                    Call<ProfileResponse> call = apiInterface.getProfileItemById(maxId);
                    int finalMaxAmt = maxAmt;
                    call.enqueue(new Callback<ProfileResponse>() {
                        @Override
                        public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                            if (response.isSuccessful()) {
                                response.body().getFullName();
                                response.body().getProfileimg();
                                binding.senderTv.setText(response.body().getFullName());
                                binding.msgTv.setText("Pay ₹ " + String.valueOf(finalMaxAmt));
                                Glide.with(getApplicationContext()).load("http://meetjob.techpanda.art" + response.body().getProfileimg()).placeholder(R.drawable.avatar).into(binding.dpImg);
                            } else {
                                Log.i("uhkdfukjsdfnsdkf", response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<ProfileResponse> call, Throwable t) {
                            Toast.makeText(BidRoomActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showPopup() {

        adDialog.setContentView(R.layout.winner_pay_popup);
        adDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        adDialog.show();

        AppCompatButton mainPayBtn = adDialog.findViewById(R.id.mainPayBtn);
        mainPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BidRoomActivity.this, MainActivity.class));
                Toast.makeText(BidRoomActivity.this, "Your Request Has Been Submitted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        adDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                startActivity(new Intent(BidRoomActivity.this, MainActivity.class));
                finish();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        myAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myAdapter.stopListening();
    }

    private void countdownFunc(String startDate, String startTime, String duration) throws ParseException {
        int durationInMin = Integer.parseInt(duration);
        String useTime = startDate + " " + startTime;
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date event_date = dateFormat.parse(useTime);

        assert event_date != null;
        long curTimeInMs = event_date.getTime();
        Date afterAddingMins = new Date(curTimeInMs + (durationInMin * 60000L));


        Date current_date = new Date();


        if (afterAddingMins.getTime() - current_date.getTime() > 0) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        handler.postDelayed(this, 1000);
                        int durationInMin = Integer.parseInt(duration);
                        String useTime = startDate + " " + startTime;
                        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                        Date event_date = dateFormat.parse(useTime);
                        assert event_date != null;
                        long curTimeInMs = event_date.getTime();
                        Date afterAddingMins = new Date(curTimeInMs + (durationInMin * 60000L));
                        Date current_date = new Date();
                        if (!current_date.after(afterAddingMins)) {
                            diff = afterAddingMins.getTime() - current_date.getTime();
                            Hours = diff / (60 * 60 * 1000) % 24;
                            Minutes = diff / (60 * 1000) % 60;
                            Seconds = diff / 1000 % 60;

                            binding.bidRecyclerView.setVisibility(View.VISIBLE);
                            binding.clWinner.setVisibility(View.GONE);

                            binding.tvHour.setText(String.format("%02d", Hours));
                            binding.tvMinute.setText(String.format("%02d", Minutes));
                            binding.tvSecond.setText(String.format("%02d", Seconds));

                        } else {
                            for (int i = 0; i < listBidders.size(); i++) {
                                Log.i("ukujndfsdf", listBidders.get(i).getName() + listBidders.get(i).getBiddingAmount() + listBidders.get(i).getId());

                            }


                            binding.startBiddingTv.setVisibility(View.VISIBLE);
                            binding.ll2.setVisibility(View.GONE);
                            binding.ll3.setVisibility(View.GONE);
                            binding.ll4.setVisibility(View.GONE);

                            binding.bidRecyclerView.setVisibility(View.GONE);
                            binding.clWinner.setVisibility(View.VISIBLE);
                            handler.removeCallbacks(runnable);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
            handler.postDelayed(runnable, 0);
        } else {


            binding.startBiddingTv.setVisibility(View.VISIBLE);
            binding.ll2.setVisibility(View.GONE);
            binding.ll3.setVisibility(View.GONE);
            binding.ll4.setVisibility(View.GONE);

            binding.bidRecyclerView.setVisibility(View.GONE);
            binding.clWinner.setVisibility(View.VISIBLE);

            // api call where we have to pass round id and patch it with is_completed=true
//            setRoundCompleted();
        }


    }

    private void setRoundCompleted() {

        Call<RoundCompletedPatchResponse> call = apiInterface.setRoundCompletedPatchById(currentRoundId, true);
        call.enqueue(new Callback<RoundCompletedPatchResponse>() {
            @Override
            public void onResponse(Call<RoundCompletedPatchResponse> call, Response<RoundCompletedPatchResponse> response) {
                if (response.isSuccessful()) {
                    Log.i("SUCCESS", response.message());
                    Log.i("PATCH RESPONSE", response.body().getMsg());
                } else {
                    Toast.makeText(BidRoomActivity.this, "some error occured", Toast.LENGTH_SHORT).show();
                    Log.i("jhdfjsdfsd", response.message());
                }
            }

            @Override
            public void onFailure(Call<RoundCompletedPatchResponse> call, Throwable t) {
                Toast.makeText(BidRoomActivity.this, "Some technical failure occured", Toast.LENGTH_SHORT).show();
                Log.i("dfjsdbfjiksdfn", t.getMessage());
            }
        });
    }

}