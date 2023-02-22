package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coinclubapp.Adapters.BidsAdapter;
import com.example.coinclubapp.Fragments.BidNowFragment;
import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityBidRoomBinding;
import com.example.coinclubapp.result.RoundsResult;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BidRoomActivity extends AppCompatActivity {
    ActivityBidRoomBinding binding;
    private String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    Handler handler = new Handler();
    Runnable runnable;

    ApiInterface apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBidRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bidRecyclerView.setAdapter(new BidsAdapter());
        binding.bidRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        int roundId = getIntent().getIntExtra("roundId", 0);

        Call<RoundsResult> call = apiInterface.getRoundsById(roundId);
        call.enqueue(new Callback<RoundsResult>() {
            @Override
            public void onResponse(Call<RoundsResult> call, Response<RoundsResult> response) {
                if (response.isSuccessful()) {
                    String startDate = String.valueOf(response.body().getStartdate());
                    String startTime = String.valueOf(response.body().getStarttime());
                    String duration = String.valueOf(response.body().getDuration());

                    countdownFunc(startDate, startTime, duration);
//                    Toast.makeText(BidRoomActivity.this, String.valueOf(response.body().getId()), Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("hlfuidfhsd", response.message());

                }
            }

            @Override
            public void onFailure(Call<RoundsResult> call, Throwable t) {
                Log.i("bjhbfdjhf", t.getMessage());

            }
        });


        BidNowFragment bidNowFragment = new BidNowFragment();
        binding.bidBtn.setOnClickListener(v -> {

            bidNowFragment.show(getSupportFragmentManager(), bidNowFragment.getTag());

            View sheetViews = LayoutInflater.from(getApplicationContext()).inflate(R.layout.fragment_bid_now, null);
            EditText et = sheetViews.findViewById(R.id.bidAmtEt);
            AppCompatButton acb = sheetViews.findViewById(R.id.myBidButton);

            acb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new CountDownTimer(1500, 1000) {
                        public void onTick(long millisUntilFinished) {
                            // Used for formatting digit to be in 2 digits only
                            binding.progressbar.setVisibility(View.VISIBLE);
                        }

                        // When the task is over it will print 00:00:00 there
                        public void onFinish() {
                            binding.progressbar.setVisibility(View.GONE);
                            binding.bidRecyclerView.setAdapter(new BidsAdapter());
                            Toast.makeText(BidRoomActivity.this, et.getText().toString() + "₹", Toast.LENGTH_SHORT).show();
                            bidNowFragment.dismiss();
                        }
                    }.start();

                }
            });

        });

    }

    private void countdownFunc(String startDate, String startTime, String duration) {
        int durationInMin = Integer.parseInt(duration);

        Calendar date = Calendar.getInstance();
        System.out.println("Current Date and TIme : " + date.getTime());
        long timeInSecs = date.getTimeInMillis();
        Date afterAdding10Mins = new Date(timeInSecs + (10 * 60 * 1000));
        System.out.println("After adding 10 mins : " + afterAdding10Mins.getTime());

        String useTime = startDate + " " + startTime;


        new CountDownTimer(50000, 1000) {
            public void onTick(long millisUntilFinished) {

                binding.startBiddingTv.setVisibility(View.GONE);
                binding.ll2.setVisibility(View.VISIBLE);
                binding.ll3.setVisibility(View.VISIBLE);
                binding.ll4.setVisibility(View.VISIBLE);

                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;

                binding.tvHour.setText(f.format(hour));
                binding.tvMinute.setText(f.format(min));
                binding.tvSecond.setText(f.format(sec));
                binding.bidBtn.setEnabled(true);
            }

            // When the task is over it will print 00:00:00 there
            public void onFinish() {

                binding.startBiddingTv.setVisibility(View.VISIBLE);
                binding.ll2.setVisibility(View.GONE);
                binding.ll3.setVisibility(View.GONE);
                binding.ll4.setVisibility(View.GONE);

                binding.tvHour.setText("");
                binding.tvMinute.setText("");
                binding.tvSecond.setText("");

                binding.clWinner.setVisibility(View.VISIBLE);
                binding.bidRecyclerView.setVisibility(View.GONE);
                binding.bidBtn.setVisibility(View.GONE);

                binding.bidBtn.setEnabled(false);
            }
        }.start();

    }


}