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
import android.view.View;
import android.widget.Toast;

import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Response.AddOrWithdrawMoneyResponsePost;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityAddMoneyBinding;
import com.example.coinclubapp.databinding.ActivityWithdrawBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WithdrawActivity extends AppCompatActivity {
    ActivityWithdrawBinding binding;
    ApiInterface apiInterface;
    int Id;
    String amount;
    Dialog adDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWithdrawBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);
        adDialog = new Dialog(WithdrawActivity.this);


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
                } else {
                    amount = binding.amountEt.getText().toString();
                    Id = sharedPreferences.getInt("Id", 0);
                    Call<AddOrWithdrawMoneyResponsePost> call = apiInterface.postWithdrawMoney(Id, amount);
                    final ProgressDialog progressDialog = new ProgressDialog(WithdrawActivity.this);
                    progressDialog.setMessage("Please Wait....");
                    progressDialog.show();
                    call.enqueue(new Callback<AddOrWithdrawMoneyResponsePost>() {
                        @Override
                        public void onResponse(Call<AddOrWithdrawMoneyResponsePost> call, Response<AddOrWithdrawMoneyResponsePost> response) {
                            if (response.isSuccessful()) {
                                progressDialog.dismiss();
                                showPopup();

                            } else {
                                Toast.makeText(WithdrawActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AddOrWithdrawMoneyResponsePost> call, Throwable t) {
                            Toast.makeText(WithdrawActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void showPopup() {

        adDialog.setContentView(R.layout.kyc_pending_popup_layout);
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