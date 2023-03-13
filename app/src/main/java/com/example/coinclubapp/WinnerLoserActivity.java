package com.example.coinclubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
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
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.coinclubapp.Adapters.LosersAdapter;
import com.example.coinclubapp.BiddingModel.Bidders;
import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Response.ListToGetIdOfRecord;
import com.example.coinclubapp.Response.PaidUnpaidListResponse;
import com.example.coinclubapp.Response.PayRecord;
import com.example.coinclubapp.Response.ProfileResponse;
import com.example.coinclubapp.Response.RoundCompletedPatchResponse;
import com.example.coinclubapp.Response.WinnerLoserTableCreate;
import com.example.coinclubapp.Response.WinnerPatchToRound;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityWinnerLoserBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public class WinnerLoserActivity extends AppCompatActivity {
    ActivityWinnerLoserBinding binding;
    String winnerName, winnerAmount, winnerImage, clubName, roundNumber;
    int winnerId, Id, roundId, maxAmt, maxId;
    ApiInterface apiInterface;
    Dialog adDialog, alreadyPaidDialog;

    Bidders bidders;
    List<Bidders> listBidders;
    List<Integer> maxBidderAmount;
    List<Integer> maxBidderId;
    int currentRoundId;
    String walletAmount;
    int recordId;
String clubAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWinnerLoserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adDialog = new Dialog(WinnerLoserActivity.this);
        alreadyPaidDialog = new Dialog(WinnerLoserActivity.this);

        apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);

        final ProgressDialog progressDialog = new ProgressDialog(WinnerLoserActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        listBidders = new ArrayList<>();
        maxBidderAmount = new ArrayList<>();
        maxBidderId = new ArrayList<>();

        Id = sharedPreferences.getInt("Id", 0);

        String clubName = getIntent().getStringExtra("ClubName");
        Log.d("CLUBNAME", clubName);

        String roundNumber = getIntent().getStringExtra("RNumber");
        Log.d("ROUNDNUMBER", roundNumber);

        String roundName = getIntent().getStringExtra("RName");
        Log.d("ROUNDNAME", roundName);

        int clubId = getIntent().getIntExtra("clubId", 0);
        Log.d("CLUBID", String.valueOf(clubId));

        currentRoundId = getIntent().getIntExtra("roundId", 0);
        Log.d("CURRENTROUNDID", String.valueOf(currentRoundId));

        roundId = currentRoundId;

        clubAmount=getIntent().getStringExtra("clubAmount");

        DatabaseReference myBidders = FirebaseDatabase.getInstance().getReference().child(clubName).child(roundName);
        myBidders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot shot : snapshot.getChildren()) {
                    bidders = shot.getValue(Bidders.class);
                    listBidders.add(bidders);
                    maxBidderId.add(bidders.getId());
                    maxBidderAmount.add(bidders.getBiddingAmount());
                    maxAmt = maxBidderAmount.get(0);
                    maxId = maxBidderId.get(0);
                    Log.d("DATA OF BIDDERS", bidders.getName() + bidders.getBiddingAmount() + bidders.getId());
                    for (int i = 0; i < maxBidderAmount.size(); i++) {
                        if (maxBidderAmount.get(i) > maxAmt) {
                            maxAmt = maxBidderAmount.get(i);
                            maxId = maxBidderId.get(i);
                        }
                    }
                }


                Call<ProfileResponse> call = apiInterface.getProfileItemById(maxId);
                int finalMaxAmt = maxAmt;

                Log.d("MAX BIDDER ID", String.valueOf(maxId));
                Log.d("MAX BIDDER ID", String.valueOf(maxAmt));

                call.enqueue(new Callback<ProfileResponse>() {
                    @Override
                    public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                        if (response.isSuccessful()) {
                            response.body().getFullName();
                            response.body().getProfileimg();
                            winnerName = response.body().getFullName();
                            winnerAmount = String.valueOf(Integer.parseInt(clubAmount)-finalMaxAmt);
                            winnerImage = (String) response.body().getProfileimg();
                            winnerId = response.body().getId();


                            Glide.with(WinnerLoserActivity.this).load("http://meetjob.techpanda.art" + winnerImage).placeholder(R.drawable.avatar).into(binding.dpImg);
                            binding.winnerName.setText(winnerName);
                            binding.winnerId.setText(String.valueOf(winnerId));
                            binding.winnerAmount.setText(winnerAmount + " ₹");
                            binding.clubName.setText(clubName);
                            binding.roundNumber.setText(roundNumber);

//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putString("clubName", clubName);
//                            editor.putString("roundNumber", roundNumber);
//                            editor.putString("winnerName", winnerName);
//                            editor.putString("winnerAmount", winnerAmount);
//                            editor.putString("winnerImage", winnerImage);
//                            editor.putInt("winnerId", winnerId);
//                            editor.putInt("currentRoundId", currentRoundId);
//                            editor.apply();


                            if (Id == winnerId) {
                                Call<WinnerPatchToRound> callPatchWinner = apiInterface.patchWinner(currentRoundId, String.valueOf(winnerId), winnerAmount);
                                callPatchWinner.enqueue(new Callback<WinnerPatchToRound>() {
                                    @Override
                                    public void onResponse(Call<WinnerPatchToRound> call, Response<WinnerPatchToRound> response) {
                                        if (response.isSuccessful()) {
                                            Call<List<WinnerLoserTableCreate>> callCreateTable = apiInterface.createWinnerLoserTable(currentRoundId);
                                            callCreateTable.enqueue(new Callback<List<WinnerLoserTableCreate>>() {
                                                @Override
                                                public void onResponse(Call<List<WinnerLoserTableCreate>> call, Response<List<WinnerLoserTableCreate>> response) {
                                                    if (response.isSuccessful()) {
                                                        Call<List<PaidUnpaidListResponse>> callPaidUnpaidList = apiInterface.paidUnpaidList(currentRoundId);
                                                        callPaidUnpaidList.enqueue(new Callback<List<PaidUnpaidListResponse>>() {
                                                            @Override
                                                            public void onResponse(Call<List<PaidUnpaidListResponse>> call, Response<List<PaidUnpaidListResponse>> response) {
                                                                if (response.isSuccessful()) {
                                                                    progressDialog.dismiss();
                                                                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(WinnerLoserActivity.this));
                                                                    binding.recyclerView.setAdapter(new LosersAdapter(response.body(), WinnerLoserActivity.this,currentRoundId));
                                                                } else {
                                                                    Toast.makeText(WinnerLoserActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                                                                    Log.d("ERROR", String.valueOf(response.code()) + response.body() + response.errorBody().toString());

                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<List<PaidUnpaidListResponse>> call, Throwable t) {
                                                                Toast.makeText(WinnerLoserActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                                                                Log.d("ERROR", t.getMessage() + t.getCause().toString());

                                                            }
                                                        });
                                                    } else {
                                                        Toast.makeText(WinnerLoserActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                                                        Log.d("ERROR", String.valueOf(response.code()) + response.body() + response.errorBody().toString());

                                                    }

                                                }

                                                @Override
                                                public void onFailure(Call<List<WinnerLoserTableCreate>> call, Throwable t) {
                                                    Toast.makeText(WinnerLoserActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                                                    Log.d("ERROR", t.getMessage() + t.getCause().toString());

                                                }
                                            });
                                        } else {
                                            Toast.makeText(WinnerLoserActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                                            Log.d("ERROR", String.valueOf(response.code()) + response.body() + response.errorBody().toString());

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<WinnerPatchToRound> call, Throwable t) {
                                        Toast.makeText(WinnerLoserActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                                        Log.d("ERROR", t.getMessage() + t.getCause().toString());

                                    }
                                });

                                binding.cardView.setVisibility(View.GONE);
                                binding.recyclerView.setVisibility(View.VISIBLE);


                            } else {

                                // to get record id of loser
                                Call<List<ListToGetIdOfRecord>> callLoser = apiInterface.getRecordIdOfLoser();
                                callLoser.enqueue(new Callback<List<ListToGetIdOfRecord>>() {
                                    @Override
                                    public void onResponse(Call<List<ListToGetIdOfRecord>> call, Response<List<ListToGetIdOfRecord>> response) {
                                        if (response.isSuccessful()) {
                                            recordId = 0;

                                            List<ListToGetIdOfRecord> allList = response.body();
                                            Log.d("hdujkfhnsdkjf", "jkdnfsdjkf");
                                            for (ListToGetIdOfRecord my : allList) {
                                                Log.d("loserId", String.valueOf(Id));
                                                Log.d("roundId", String.valueOf(roundId));
                                                if (Id == my.getLooser() && roundId == my.getRoundpayment()) {

                                                    Log.d("paymentRecordMeLoserId", String.valueOf(my.getLooser()));
                                                    Log.d("paymentRecordMeRoundId", String.valueOf(my.getRoundpayment()));
                                                    recordId = my.getId();
                                                }
                                            }

                                            Log.d("recordIdBahar", String.valueOf(recordId));

                                            Call<ListToGetIdOfRecord> listToGetIdOfRecordCall = apiInterface.getAllRecordsAndCompare(recordId);
                                            listToGetIdOfRecordCall.enqueue(new Callback<ListToGetIdOfRecord>() {
                                                @Override
                                                public void onResponse(Call<ListToGetIdOfRecord> call, Response<ListToGetIdOfRecord> response) {
                                                    if (response.isSuccessful()) {
                                                        Log.d("resp", String.valueOf(recordId));
                                                        if (response.body().getIsPaid()) {
                                                            progressDialog.dismiss();
                                                            showPaidPopup();
                                                            // paid he

                                                        } else {
                                                            progressDialog.dismiss();
                                                            binding.cardView.setVisibility(View.VISIBLE);
                                                            binding.recyclerView.setVisibility(View.GONE);
                                                            // unpaid he

                                                        }
                                                    } else {
                                                        progressDialog.dismiss();
                                                        Toast.makeText(WinnerLoserActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                                        Log.d("ERROR", response.message() + "hjkfsdkfhnsdf" + response.errorBody().toString());
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<ListToGetIdOfRecord> call, Throwable t) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(WinnerLoserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                    Log.d("ERROR", t.getMessage() + "4");
                                                }
                                            });
                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(WinnerLoserActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                            Log.d("ERROR", response.message() + "12211");

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<ListToGetIdOfRecord>> call, Throwable t) {
                                        progressDialog.dismiss();
                                        Toast.makeText(WinnerLoserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                        Log.d("ERROR", t.getMessage() + " 2 ");
                                    }
                                });
                                Log.d("recordId", String.valueOf(recordId));

                                binding.payBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        progressDialog.show();

                                        Call<ProfileResponse> myProfile = apiInterface.getProfileItemById(Id);
                                        myProfile.enqueue(new Callback<ProfileResponse>() {
                                            @Override
                                            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                                                if (response.isSuccessful()) {
                                                    progressDialog.dismiss();
                                                    int myWalletAmount = Integer.parseInt(response.body().getWalletAmount());

                                                    int amountToBePaid = Integer.parseInt(winnerAmount);
                                                    Log.d("ERROR", String.valueOf(amountToBePaid) + "    " + myWalletAmount);
                                                    if (myWalletAmount < amountToBePaid) {
                                                        showPopup();
                                                    } else {

                                                        // we have record id , now apn record id se check krenge k is paid true he kya
                                                        // agr false he to payment send krne ka btaenge
                                                        // agar true he to next round ka wait krne ka bolenge

                                                        Date date = new Date();
                                                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                                                        String payment_time = formatter.format(date);
                                                        Call<PayRecord> payRecordCall = apiInterface.patchpayment(recordId, true, winnerAmount, payment_time);
                                                        payRecordCall.enqueue(new Callback<PayRecord>() {
                                                            @Override
                                                            public void onResponse(Call<PayRecord> call, Response<PayRecord> response) {
                                                                if (response.isSuccessful()) {
                                                                    Toast.makeText(WinnerLoserActivity.this, "payment Successful", Toast.LENGTH_SHORT).show();
                                                                    startActivity(new Intent(WinnerLoserActivity.this, MainActivity.class));
                                                                    finish();
                                                                } else {
                                                                    Toast.makeText(WinnerLoserActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                                                    Log.d("ERROR", response.message() + response.code());
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<PayRecord> call, Throwable t) {
                                                                Toast.makeText(WinnerLoserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                                Log.d("ERROR", t.getMessage());
                                                            }
                                                        });

                                                    }

                                                } else {
                                                    Toast.makeText(WinnerLoserActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                                    Log.d("ERROR", response.message() + response.code() + response.errorBody().toString());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                                                Toast.makeText(WinnerLoserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                Log.d("ERROR", t.getMessage());
                                            }
                                        });
                                    }
                                });
                            }

                        } else {
                            Log.i("uhkdfukjsdfnsdkf", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ProfileResponse> call, Throwable t) {
                        Toast.makeText(WinnerLoserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void showPaidPopup() {
        alreadyPaidDialog.setContentView(R.layout.already_paid_please_wait_popup_layout);
        alreadyPaidDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alreadyPaidDialog.show();

        AppCompatButton okBtn = alreadyPaidDialog.findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WinnerLoserActivity.this, MainActivity.class));
                finish();
            }
        });
        alreadyPaidDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                startActivity(new Intent(WinnerLoserActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    private void showPopup() {

        adDialog.setContentView(R.layout.insufficient_balance_popup_layout);
        adDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        adDialog.show();

        AppCompatButton okBtn = adDialog.findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WinnerLoserActivity.this, MyBankActivity.class));
                finish();
            }
        });
        adDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                startActivity(new Intent(WinnerLoserActivity.this, MyBankActivity.class));
                finish();
            }
        });
    }


}