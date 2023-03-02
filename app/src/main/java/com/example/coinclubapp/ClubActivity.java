package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coinclubapp.Adapters.Average_round_Adapter;
import com.example.coinclubapp.Adapters.Average_time_Adapter;
import com.example.coinclubapp.Adapters.MemberAdapter;
import com.example.coinclubapp.Adapters.RoundAdapter;
import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Response.AllClubsGet;
import com.example.coinclubapp.Response.ClubUserResponse;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityClubBinding;
import com.example.coinclubapp.result.Clubuser;
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
    List<Clubuser> clubusers;
    ApiInterface apiInterface;
    String startDATE, startTIME, clubName, roundName, RNumber;
    Average_round_Adapter average_round_adapter;
    Average_time_Adapter average_time_adapter;
    int roundId;
    String duration;
    String useTime;
    Dialog adDialog;
    String fromWhere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClubBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);
        String clubId = getIntent().getStringExtra("id");
clubusers=new ArrayList<>();
        binding.bidStartIn.setEnabled(false);
        binding.clubName.setText(getIntent().getStringExtra("clubName"));
        fromWhere = getIntent().getStringExtra("fromWhere");
        adDialog = new Dialog(ClubActivity.this);
        average_round_adapter = new Average_round_Adapter();
        average_time_adapter = new Average_time_Adapter();

        final ProgressDialog progressDialog = new ProgressDialog(ClubActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        binding.info2Btn.setOnClickListener(v -> {
            showPopup();
        });

        binding.infoBtn.setOnClickListener(v -> {
            showPopup2();
        });


        Call<AllClubsGet> callClubs = apiInterface.getClubById(Integer.parseInt(clubId));
        callClubs.enqueue(new Callback<AllClubsGet>() {
            @Override
            public void onResponse(Call<AllClubsGet> call, Response<AllClubsGet> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
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
                    //jhkfhkjsdfsdjkf yogesh
                    i.putExtra("roundId", roundId);
                    i.putExtra("duration", duration);
                    i.putExtra("startDate", startDATE);
                    i.putExtra("startTime", startTIME);
                    i.putExtra("ClubName", clubName);
                    i.putExtra("RName", roundName);
                    i.putExtra("RNumber", RNumber);

                    startActivity(i);
                } else {
                    Toast.makeText(ClubActivity.this, "Button Is Disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.backBtn.setOnClickListener(v -> {
            Intent i = new Intent(ClubActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        });

        layoutManagerM = new LinearLayoutManager(ClubActivity.this, LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerViewMember.setLayoutManager(layoutManagerM);


        layoutManagerR = new LinearLayoutManager(ClubActivity.this, LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerViewRound.setLayoutManager(layoutManagerR);


        Call<List<ClubUserResponse>>call=apiInterface.getUsersByClubId(Integer.parseInt(clubId));
        call.enqueue(new Callback<List<ClubUserResponse>>() {
            @Override
            public void onResponse(Call<List<ClubUserResponse>> call, Response<List<ClubUserResponse>> response) {
                if (response.isSuccessful()) {
                     clubusers=response.body().get(0).getClubuser();
                    binding.recyclerViewMember.setAdapter(new MemberAdapter(ClubActivity.this, clubusers));
                } else {
                    Toast.makeText(ClubActivity.this, "Some Error Occured While Getting Members", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ClubUserResponse>> call, Throwable t) {
                Toast.makeText(ClubActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
            }
        });


        Call<List<RoundsResult>> callR = apiInterface.getAllRounds();
        callR.enqueue(new Callback<List<RoundsResult>>() {
            @Override
            public void onResponse(Call<List<RoundsResult>> call, Response<List<RoundsResult>> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    List<RoundsResult> myRounds = new ArrayList<>();
                    for (int i = 0; i < response.body().size(); i++) {
                        if (Objects.equals(getIntent().getStringExtra("clubName"), response.body().get(i).getClubname())) {
                            myRounds.add(response.body().get(i));
                        }

                    }

                    for (int i = 0; i < myRounds.size(); i++) {
                        RoundsResult roundJiskiBidStartHogi = new RoundsResult();
                        if (!myRounds.get(i).getIsCompleted()) {
                            roundJiskiBidStartHogi = myRounds.get(i);
                            startDATE = String.valueOf(roundJiskiBidStartHogi.getStartdate());
                            startTIME = String.valueOf(roundJiskiBidStartHogi.getStarttime());
                            useTime = startDATE + " " + startTIME;
                            duration = String.valueOf(roundJiskiBidStartHogi.getDuration());
                            roundId = roundJiskiBidStartHogi.getId();
                            clubName =roundJiskiBidStartHogi.getClubname();
                            roundName = roundJiskiBidStartHogi.getRoundname();
                            RNumber = roundJiskiBidStartHogi.getRoundno();

                            Log.i("jkndfjdnfidf round id", String.valueOf(roundId));

                            if (fromWhere.equalsIgnoreCase("hotclubactivity")) {
                                binding.bidStartIn.setEnabled(false);
                                binding.bidStartIn.setVisibility(View.GONE);
                                binding.recyclerViewRound.setAdapter(new RoundAdapter(myRounds, roundId));

                            } else {
                                try {
                                    countDownFunc(useTime);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                } finally {
                                    binding.recyclerViewRound.setAdapter(new RoundAdapter(myRounds, roundId));
                                    break;
                                }
                            }

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
            i.putExtra("clubId",clubId);
            startActivity(i);
        });
    }

    private void showPopup2() {
        adDialog.setContentView(R.layout.average_round_time_popup);
        adDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        adDialog.show();

        RecyclerView rv = adDialog.findViewById(R.id.rv);
        TextView tv = adDialog.findViewById(R.id.textView6);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(average_time_adapter);
    }

    private void showPopup() {
        adDialog.setContentView(R.layout.club_activity_info_popup);
        adDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        adDialog.show();

        RecyclerView rv = adDialog.findViewById(R.id.rv);
        TextView tv = adDialog.findViewById(R.id.textView6);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(average_round_adapter);


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
                                binding.bidStartIn.setText("Start Bidding");
                                binding.bidStartIn.setTextColor(Color.WHITE);
                                binding.bidStartIn.setBackgroundResource(R.drawable.start_bidding_bg);
                                binding.bidStartIn.setEnabled(true);
                                handler.removeCallbacks(null);
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
