package com.example.coinclubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

import android.view.View;

import android.widget.Toast;


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

public class BidRoomActivity extends AppCompatActivity {
    ActivityBidRoomBinding binding;
    private final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    static long diff, Hours, Minutes, Seconds;
    int currentRoundId;
    Handler handler = new Handler();
    Runnable runnable;
    BidNowFragment bidNowFragment;
    ApiInterface apiInterface;
    String roundName, clubName, roundNumber, name, RName, RNumber;

    LinearLayoutManager linearLayoutManager;
    MyAdapter myAdapter;
    Bidders bidders;
    List<Bidders> listBidders;
    List<Integer> maxBidderAmount;
    List<Integer> maxBidderId;
    DatabaseReference myBidders;
    Dialog adDialog;
    int maxAmt;

    String winnerName, winnerAmount, winnerImage;
    int winnerId;

    Intent winnerLoserIntent;

    int clubId;

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
        winnerLoserIntent = new Intent(BidRoomActivity.this, WinnerLoserActivity.class);
        apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);

        LinearLayoutManager layoutManager = new LinearLayoutManager(BidRoomActivity.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        binding.bidRecyclerView.setLayoutManager(layoutManager);

        listBidders = new ArrayList<>();


        name = getIntent().getStringExtra("ClubName");
        RName = getIntent().getStringExtra("RName");
        RNumber = getIntent().getStringExtra("RNumber");
        clubId = getIntent().getIntExtra("clubId", 0);
        currentRoundId = getIntent().getIntExtra("roundId", 0);


        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        int Id = sharedPreferences.getInt("Id", 0);


        Call<RoundsResult> call = apiInterface.getRoundsById(currentRoundId);

        call.enqueue(new Callback<RoundsResult>() {
            @Override
            public void onResponse(Call<RoundsResult> call, Response<RoundsResult> response) {
                if (response.isSuccessful()) {
                    String startDate = String.valueOf(response.body().getStartdate());
                    String startTime = String.valueOf(response.body().getStarttime());
                    String duration = String.valueOf(response.body().getDuration());
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

        binding.txtName.setText(name);

        binding.bidBtn.setOnClickListener(v -> {

            myBidders = FirebaseDatabase.getInstance().getReference().child(name).child(RName);
            myBidders.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot shot : snapshot.getChildren()) {
                        bidders = shot.getValue(Bidders.class);
                        maxBidderAmount.add(bidders.getBiddingAmount());
                        maxAmt = maxBidderAmount.get(0);
                        for (int i = 0; i < maxBidderAmount.size(); i++) {
                            if (maxBidderAmount.get(i) > maxAmt) {
                                maxAmt = maxBidderAmount.get(i);
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
            Bundle bundle = new Bundle();
            bundle.putInt("Id", Id);
            bundle.putString("clubName", name);
            bundle.putString("roundName", RName);
            bundle.putInt("roundId", currentRoundId);
//            bundle.putInt("lastBidAmt",maxAmt);
            bidNowFragment.setArguments(bundle);
            bidNowFragment.show(getSupportFragmentManager(), bidNowFragment.getTag());
        });

        FirebaseRecyclerOptions<Bidders> options = new FirebaseRecyclerOptions.Builder<Bidders>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child(name).child(RName).orderByChild("biddingAmount"), Bidders.class)
                .build();
        myAdapter = new MyAdapter(options, BidRoomActivity.this);
        binding.bidRecyclerView.setAdapter(myAdapter);
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


                            binding.tvHour.setText(String.format("%02d", Hours));
                            binding.tvMinute.setText(String.format("%02d", Minutes));
                            binding.tvSecond.setText(String.format("%02d", Seconds));

                        } else {
//                            setRoundCompleted();


                            binding.bidBtn.setEnabled(false);

                            binding.startBiddingTv.setVisibility(View.VISIBLE);
                            binding.ll2.setVisibility(View.GONE);
                            binding.ll3.setVisibility(View.GONE);
                            binding.ll4.setVisibility(View.GONE);
                            binding.bidBtn.setEnabled(false);


                            binding.bidRecyclerView.setVisibility(View.GONE);


                            winnerLoserIntent.putExtra("ClubName", name);
                            winnerLoserIntent.putExtra("RNumber", RNumber);
                            winnerLoserIntent.putExtra("clubId", clubId);
                            winnerLoserIntent.putExtra("RName", RName);
                            winnerLoserIntent.putExtra("roundId", currentRoundId);

                            Log.i("hsfnsdfksdn", name + RNumber + name + clubId + RName);


                            startActivity(winnerLoserIntent);
                            finish();

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

            winnerLoserIntent.putExtra("ClubName", name);
            winnerLoserIntent.putExtra("RNumber", RNumber);
            winnerLoserIntent.putExtra("clubId", clubId);
            winnerLoserIntent.putExtra("RName", RName);
            winnerLoserIntent.putExtra("roundId", currentRoundId);

            Log.i("hsfnsdfksdn", name + RNumber + name + clubId + RName);


            startActivity(winnerLoserIntent);
            finish();

            // api call where we have to pass round id and patch it with is_completed=true
//            setRoundCompleted();
        }
    }

//    private void setRoundCompleted() {
//
//        Call<RoundCompletedPatchResponse> call = apiInterface.setRoundCompletedPatchById(currentRoundId, true);
//        call.enqueue(new Callback<RoundCompletedPatchResponse>() {
//            @Override
//            public void onResponse(Call<RoundCompletedPatchResponse> call, Response<RoundCompletedPatchResponse> response) {
//                if (response.isSuccessful()) {
//                    Log.i("SUCCESS", response.message());
//                    Log.i("PATCH RESPONSE", response.body().getMsg());
//                } else {
//                    Toast.makeText(BidRoomActivity.this, "some error occured", Toast.LENGTH_SHORT).show();
//                    Log.i("jhdfjsdfsd", response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RoundCompletedPatchResponse> call, Throwable t) {
//                Toast.makeText(BidRoomActivity.this, "Some technical failure occured", Toast.LENGTH_SHORT).show();
//                Log.i("dfjsdbfjiksdfn", t.getMessage());
//            }
//        });
//    }

}