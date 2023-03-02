package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Response.AddOrWithdrawMoneyResponsePost;
import com.example.coinclubapp.Response.ProfileResponse;
import com.example.coinclubapp.Response.WithdrawMoneyResponse;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityAddMoneyBinding;
import com.example.coinclubapp.databinding.ActivityWithdrawBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class WithdrawActivity extends AppCompatActivity {
    ActivityWithdrawBinding binding;
    ApiInterface apiInterface;
    int Id;
    String amount;
    Dialog adDialog;
    Date date;
    Date oldDate;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    String wallet = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWithdrawBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);
        adDialog = new Dialog(WithdrawActivity.this);
        Id = sharedPreferences.getInt("Id", 0);


        Call<ProfileResponse> call = apiInterface.getProfileItemById(Id);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()) {
                    wallet = response.body().getWalletAmount();
                } else {
                    Toast.makeText(WithdrawActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Toast.makeText(WithdrawActivity.this, "Some Failure Occured", Toast.LENGTH_SHORT).show();
            }
        });


        binding.twohundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.amountEt.setText("200");
            }
        });

        binding.fivehundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.amountEt.setText("500");
            }
        });

        binding.thousand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.amountEt.setText("1000");
            }
        });

        binding.twoThousand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.amountEt.setText("2000");
            }
        });

        binding.fiveThousand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.amountEt.setText("5000");
            }
        });


        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.amountEt.getText().toString().isEmpty()) {
                    binding.amountEt.setError("Enter Amount");
                    binding.amountEt.requestFocus();
                } else if (wallet==null) {
                    Toast.makeText(WithdrawActivity.this, "Some Error Occured , Please Try Again After Some Time", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(binding.amountEt.getText().toString()) > Integer.parseInt(wallet)) {
                    Toast.makeText(WithdrawActivity.this, "You don't have enough amount in your wallet", Toast.LENGTH_SHORT).show();
                } else {
                    amount = binding.amountEt.getText().toString();

                    Call<WithdrawMoneyResponse> call = apiInterface.withdrawMoneyPost(Id, amount);
                    final ProgressDialog progressDialog = new ProgressDialog(WithdrawActivity.this);
                    progressDialog.setMessage("Please Wait....");
                    progressDialog.show();
                    if (sharedPreferences.contains("lastWithdrawDate")) {
                        try {
                            String temp = sharedPreferences.getString("lastWithdrawDate", null);
                            oldDate = dateFormat.parse(temp);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        date = new Date();

                        Log.i("old date jb phle se he", String.valueOf(oldDate.getTime()));
                        Log.i("Current date", String.valueOf(date.getTime()));

                        long curTimeInMs = oldDate.getTime();
                        Date afterAddingMins = new Date(curTimeInMs + (1440 * 60000L));

                        if (date.after(afterAddingMins)) {
                            call.enqueue(new Callback<WithdrawMoneyResponse>() {
                                @Override
                                public void onResponse(Call<WithdrawMoneyResponse> call, Response<WithdrawMoneyResponse> response) {
                                    if (response.isSuccessful()) {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        String dateTime = dateFormat.format(date);
                                        editor.putString("lastWithdrawDate", dateTime);
                                        editor.apply();
                                        progressDialog.dismiss();
                                        showPopup();
                                    } else {
                                        Toast.makeText(WithdrawActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<WithdrawMoneyResponse> call, Throwable t) {
                                    Toast.makeText(WithdrawActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(WithdrawActivity.this, "can't request more than one withdrawal in a single working day", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        date = new Date();
                        String dateTime = dateFormat.format(date);
                        editor.putString("lastWithdrawDate", dateTime);
                        editor.apply();
                        call.enqueue(new Callback<WithdrawMoneyResponse>() {
                            @Override
                            public void onResponse(Call<WithdrawMoneyResponse> call, Response<WithdrawMoneyResponse> response) {
                                if (response.isSuccessful()) {
                                    progressDialog.dismiss();
                                    showPopup();
                                } else {
                                    Toast.makeText(WithdrawActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<WithdrawMoneyResponse> call, Throwable t) {
                                Toast.makeText(WithdrawActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            }
        });
    }

    private void showPopup() {

        adDialog.setContentView(R.layout.withdraw_popup);
        adDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        adDialog.show();

        AppCompatButton okBtn = adDialog.findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WithdrawActivity.this, MainActivity.class));
                finish();
            }
        });
        adDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                startActivity(new Intent(WithdrawActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}