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
import com.example.coinclubapp.Response.ClubUserResponse;
import com.example.coinclubapp.Response.ListToGetIdOfRecord;
import com.example.coinclubapp.Response.ProfileResponse;
import com.example.coinclubapp.Response.RoundCompletedPatchResponse;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityBidRoomBinding;
import com.example.coinclubapp.result.Clubuser;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

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
    String minAmount;
    LinearLayoutManager linearLayoutManager;
    MyAdapter myAdapter;
    Bidders bidders;
    List<Bidders> listBidders;
    List<Integer> maxBidderAmount;
    List<Integer> maxBidderId;
    List<Integer> membersId;
    List<Integer> winnersId;
    DatabaseReference myBidders;
    int lastMemberId;
    Dialog adDialog;
    int maxAmt;
    String clubMembers;
    String winnerName, winnerAmount, winnerImage;
    int winnerId;

    Intent winnerLoserIntent;

    int clubId;

    String clubAmount;

    List<ClubUserResponse> allUsersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBidRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bidNowFragment = new BidNowFragment();
        linearLayoutManager = new LinearLayoutManager(this);
        maxBidderAmount = new ArrayList<>();
        membersId = new ArrayList<>();
        winnersId =new ArrayList<>();
        maxBidderId = new ArrayList<>();
        adDialog = new Dialog(BidRoomActivity.this);
        winnerLoserIntent = new Intent(BidRoomActivity.this, WinnerLoserActivity.class);
        apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        int Id = sharedPreferences.getInt("Id", 0);

        name = getIntent().getStringExtra("ClubName");
        RName = getIntent().getStringExtra("RName");
        RNumber = getIntent().getStringExtra("RNumber");
        clubId = getIntent().getIntExtra("clubId", 0);
        currentRoundId = getIntent().getIntExtra("roundId", 0);
        clubAmount=getIntent().getStringExtra("clubAmount");
        clubMembers=getIntent().getStringExtra("clubMembers");

        binding.txtName.setText(name);


        Call<List<ListToGetIdOfRecord>> previousWinners = apiInterface.getRecordIdOfLoser();
        previousWinners.enqueue(new Callback<List<ListToGetIdOfRecord>>() {
            @Override
            public void onResponse(Call<List<ListToGetIdOfRecord>> call, Response<List<ListToGetIdOfRecord>> response) {
                if (response.isSuccessful()) {
                    binding.bidBtn.setEnabled(true);
                    winnerId = 0;
                    List<ListToGetIdOfRecord> allList = response.body();
                    for (ListToGetIdOfRecord my : allList) {
                        if (Id == my.getWinner()) {
                            Log.d("loserId", String.valueOf(Id));
                            Log.d("getMyLoser", String.valueOf(my.getLooser()));
                            winnerId = my.getWinner();
                        }
                    }

                } else {
                    binding.bidBtn.setEnabled(false);
                    Toast.makeText(BidRoomActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    Log.d("ERROR", response.message() + response.code());
                }

            }

            @Override
            public void onFailure(Call<List<ListToGetIdOfRecord>> call, Throwable t) {
                Toast.makeText(BidRoomActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ERROR", t.getMessage());
                binding.bidBtn.setEnabled(false);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(BidRoomActivity.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        binding.bidRecyclerView.setLayoutManager(layoutManager);

        listBidders = new ArrayList<>();


        Call<RoundsResult> call = apiInterface.getRoundsById(currentRoundId);

        call.enqueue(new Callback<RoundsResult>() {
            @Override
            public void onResponse(Call<RoundsResult> call, Response<RoundsResult> response) {
                if (response.isSuccessful()) {
                    String startDate = String.valueOf(response.body().getStartdate());
                    String startTime = String.valueOf(response.body().getStarttime());
                    String duration = String.valueOf(response.body().getDuration());
                    minAmount = String.valueOf(response.body().getMinbid());
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
            Log.d("MYID", String.valueOf(Id));
            Log.d("WINNERID", String.valueOf(winnerId));

            if (Id == winnerId) {
                Call<List<ClubUserResponse>> allMyUsers = apiInterface.getUsersByClubId(clubId);
                allMyUsers.enqueue(new Callback<List<ClubUserResponse>>() {
                    @Override
                    public void onResponse(Call<List<ClubUserResponse>> call, Response<List<ClubUserResponse>> response) {
                        if(response.isSuccessful())
                        {
                            ClubUserResponse singleUnit = response.body().get(0);
                            List<Clubuser> clubUsers = singleUnit.getClubuser();
                            membersId.clear();
                            Log.d("respaara1", "respaara1");
                            for (Clubuser singleUser : clubUsers) {
                                membersId.add(singleUser.getUserid());
                            }
                            // apne paas saare members ki list he membersId me
                            winnersId.clear();
                            Call<List<RoundsResult>> myRoundResultsCall = apiInterface.getAllRounds();
                            myRoundResultsCall.enqueue(new Callback<List<RoundsResult>>() {
                                @Override
                                public void onResponse(Call<List<RoundsResult>> call, Response<List<RoundsResult>> response) {
                                    if(response.isSuccessful())
                                    {
                                        Log.d("respaara", "respaara");
                                        List<RoundsResult> allRounds = response.body();
                                        List<RoundsResult> sortedRounds = new ArrayList<>();
                                        Log.d("fsdnfsdjkf",name);
                                        for (RoundsResult rr : allRounds) {
                                            if (rr.getClubname().equals(name)) {
                                                sortedRounds.add(rr);
                                            }
                                        }
                                        Log.d("sortedRounds",sortedRounds.toString());
                                        //membersId he apne paas
                                        // ab chahiye winners id;
                                        for (RoundsResult singleRound : sortedRounds) {
                                            if (singleRound.getWinner().equals("0")) {
                                            }
                                            else
                                            {
                                                winnersId.add(Integer.valueOf(singleRound.getWinner()));
                                            }
                                        }
                                        // membersId and winnersId he apne paas
                                        Log.d("WinnersIdset", winnersId.toString());
                                        Log.d("membersIdset", membersId.toString());
                                        List<Integer> differences = new ArrayList<>(membersId);
                                        differences.removeAll(winnersId);
                                        Log.d("differences",differences.toString());

                                        if (differences.size() == 1) {
//                                            DatabaseReference myReference = FirebaseDatabase.getInstance().getReference().child(clubName).child(roundName);
//                                            String myKey = String.valueOf(response.body().getMobileno());
//                                            Bidders bidders = new Bidders(response.body().getId(), response.body().getFullName(), amount);
//                                            Log.i("TAG", response.body().getId() + "  " + response.body().getFullName() + "  " + amount);
//                                            myReference.child(myKey).setValue(bidders);
                                            lastMemberId = differences.get(0);
                                            bid(minAmount, lastMemberId);
//                                            bundle.putString("minAmount", minAmount);
//                                            bundle.putBoolean("lastUser", true);
//                                            bundle.putInt("lastMemberId",lastMemberId);
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(BidRoomActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                        Log.i("jhdfjsdfsd", response.message());
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<RoundsResult>> call, Throwable t) {
                                    Toast.makeText(BidRoomActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.i("dfjsdbfjiksdfn", t.getMessage());
                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(BidRoomActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                            Log.i("jhdfjsdfsd", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ClubUserResponse>> call, Throwable t) {
                        Toast.makeText(BidRoomActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("dfjsdbfjiksdfn", t.getMessage());
                    }
                });
                Toast.makeText(BidRoomActivity.this, "you cannot bid", Toast.LENGTH_SHORT).show();
            } else {
                // agr ek hi user bacha he to true pass kro
                // agr or bche he to false pass kro
                Call<List<ClubUserResponse>> allUsers = apiInterface.getUsersByClubId(clubId);
                allUsers.enqueue(new Callback<List<ClubUserResponse>>() {
                    @Override
                    public void onResponse(Call<List<ClubUserResponse>> call, Response<List<ClubUserResponse>> response) {
                        if (response.isSuccessful()) {
                            ClubUserResponse singleUnit = response.body().get(0);
                            List<Clubuser> clubUsers = singleUnit.getClubuser();
                            membersId.clear();
                            Log.d("respaara1", "respaara1");
                            for (Clubuser singleUser : clubUsers) {
                                membersId.add(singleUser.getUserid());
                            }
                            // apne paas saare members ki list he membersId me
                            winnersId.clear();
                            Call<List<RoundsResult>> roundWinnersCall = apiInterface.getAllRounds();
                            roundWinnersCall.enqueue(new Callback<List<RoundsResult>>() {
                                @Override
                                public void onResponse(Call<List<RoundsResult>> call, Response<List<RoundsResult>> response) {
                                    if (response.isSuccessful()) {
                                        Log.d("respaara", "respaara");
                                        List<RoundsResult> allRounds = response.body();
                                        List<RoundsResult> sortedRounds = new ArrayList<>();
                                        Log.d("fsdnfsdjkf",name);
                                        for (RoundsResult rr : allRounds) {
                                            if (rr.getClubname().equals(name)) {
                                                sortedRounds.add(rr);
                                            }
                                        }
                                        Log.d("sortedRounds",sortedRounds.toString());
                                        //membersId he apne paas
                                        // ab chahiye winners id;
                                        for (RoundsResult singleRound : sortedRounds) {
                                            if (singleRound.getWinner().equals("0")) {
                                            }
                                            else
                                            {
                                                winnersId.add(Integer.valueOf(singleRound.getWinner()));
                                            }
                                        }
                                        // membersId and winnersId he apne paas
                                        Log.d("WinnersIdset", winnersId.toString());
                                        Log.d("membersIdset", membersId.toString());
                                        List<Integer> differences = new ArrayList<>(membersId);
                                        differences.removeAll(winnersId);
                                        Log.d("differences",differences.toString());

                                        Bundle bundle = new Bundle();
                                        bundle.putInt("Id", Id);
                                        bundle.putString("clubName", name);
                                        bundle.putString("roundName", RName);
                                        bundle.putInt("roundId", currentRoundId);

                                        if (differences.size() == 1) {

//                                            DatabaseReference myReference = FirebaseDatabase.getInstance().getReference().child(clubName).child(roundName);
//                                            String myKey = String.valueOf(response.body().getMobileno());
//                                            Bidders bidders = new Bidders(response.body().getId(), response.body().getFullName(), amount);
//                                            Log.i("TAG", response.body().getId() + "  " + response.body().getFullName() + "  " + amount);
//                                            myReference.child(myKey).setValue(bidders);

                                            lastMemberId = differences.get(0);
                                            bid(minAmount, lastMemberId);

//                                            bundle.putString("minAmount", minAmount);
//                                            bundle.putBoolean("lastUser", true);
//                                            bundle.putInt("lastMemberId",lastMemberId);
                                        } else {
                                            bundle.putBoolean("lastUser", false);
                                        }
                                        bidNowFragment.setArguments(bundle);
                                        bidNowFragment.show(getSupportFragmentManager(), bidNowFragment.getTag());

                                    } else {
                                        Toast.makeText(BidRoomActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                        Log.i("jhdfjsdfsd", response.message());
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<RoundsResult>> call, Throwable t) {
                                    Toast.makeText(BidRoomActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.i("dfjsdbfjiksdfn", t.getMessage());
                                }
                            });


                        } else {
                            Toast.makeText(BidRoomActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                            Log.i("jhdfjsdfsd", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ClubUserResponse>> call, Throwable t) {
                        Toast.makeText(BidRoomActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("dfjsdbfjiksdfn", t.getMessage());
                    }
                });

            }
            // agar me purane round me winner tha to agle round me meri id purane
            // winners ki id se compare hogi and match hogi
            // to me agle round me bidding nhi kr skta
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

    private void countdownFunc(String startDate, String startTime, String duration) throws
            ParseException {
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
                            winnerLoserIntent.putExtra("clubAmount",clubAmount);
                            winnerLoserIntent.putExtra("clubMembers",clubMembers);

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
            winnerLoserIntent.putExtra("clubAmount",clubAmount);
            winnerLoserIntent.putExtra("clubMembers",clubMembers);


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
    private void bid(String amtamt, int Id) {

        Integer amount = Integer.parseInt(amtamt);
        Call<ProfileResponse> call = apiInterface.getProfileItemById(Id);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()) {

                    DatabaseReference myReference = FirebaseDatabase.getInstance().getReference().child(name).child(RName);
                    String myKey = String.valueOf(response.body().getMobileno());
                    Bidders bidders = new Bidders(response.body().getId(), response.body().getFullName(), amount);
                    Log.i("TAG", response.body().getId() + "  " + response.body().getFullName() + "  " + amount);
                    myReference.child(myKey).setValue(bidders);
                } else {
                    Log.i("sdfjsifnsd", response.message());
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Log.i("hduifnhsdfi", t.getMessage());
            }
        });
    }

}