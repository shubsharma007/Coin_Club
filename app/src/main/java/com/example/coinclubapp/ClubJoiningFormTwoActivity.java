package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Response.UserRegistrationPost;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityClubJoiningFormTwoBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubJoiningFormTwoActivity extends AppCompatActivity {
    ActivityClubJoiningFormTwoBinding binding;
    ProgressDialog progress;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClubJoiningFormTwoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);


        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress = new ProgressDialog(ClubJoiningFormTwoActivity.this);
                progress.setMessage("Please Wait....");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);


                if (binding.amountEt.getText().toString().isEmpty()) {
                    binding.amountEt.setError("enter the amount");
                    binding.amountEt.requestFocus();
                } else if (!binding.rbSaving.isChecked() && !binding.rbBorrowing.isChecked() && !binding.rbInvesting.isChecked()) {
                    Toast.makeText(ClubJoiningFormTwoActivity.this, "select motivation", Toast.LENGTH_SHORT).show();
                } else if (binding.incomeEt.getText().toString().isEmpty()) {
                    binding.incomeEt.setError("enter your income");
                    binding.incomeEt.requestFocus();
                } else {
                    progress.show();
                    ApiInterface apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);
                    String motive = "";
                    if (binding.rbBorrowing.isChecked()) {
                        motive = "borrowing";
                    } else if (binding.rbInvesting.isChecked()) {
                        motive = "investing";
                    } else {
                        motive = "saving";
                    }
                    String mobile = getIntent().getStringExtra("mobile");

                    String password = getIntent().getStringExtra("password");

                    String name = getIntent().getStringExtra("full_name");

                    String gender = getIntent().getStringExtra("gender");

                    String city = getIntent().getStringExtra("city");

                    String occupation = getIntent().getStringExtra("occupation");

                    String organisation = getIntent().getStringExtra("organization");

                    String monthlycontribution = binding.amountEt.getText().toString();

                    String income = binding.incomeEt.getText().toString();


                    Call<UserRegistrationPost> call=apiInterface.registerUser(name,mobile,city,password,gender,occupation,motive,income,monthlycontribution,organisation);
                    call.enqueue(new Callback<UserRegistrationPost>() {
                        @Override
                        public void onResponse(Call<UserRegistrationPost> call, Response<UserRegistrationPost> response) {
                            if(response.isSuccessful())
                            {
                                Integer Id=response.body().getResult().getId();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("Id", Id);
                                editor.apply();
                                Toast.makeText(ClubJoiningFormTwoActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ClubJoiningFormTwoActivity.this,MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(ClubJoiningFormTwoActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<UserRegistrationPost> call, Throwable t) {
                            Log.i("ONFAILURE",t.getMessage());
                            Toast.makeText(ClubJoiningFormTwoActivity.this, "please try again", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}