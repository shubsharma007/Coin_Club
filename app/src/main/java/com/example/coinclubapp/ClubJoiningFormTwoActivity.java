package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Response.Response;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityClubJoiningFormTwoBinding;
import com.example.coinclubapp.result.Result;

import retrofit2.Call;
import retrofit2.Callback;

public class ClubJoiningFormTwoActivity extends AppCompatActivity {
    ActivityClubJoiningFormTwoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClubJoiningFormTwoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.amountEt.getText().toString().isEmpty()) {
                    binding.amountEt.setError("enter the amount");
                    binding.amountEt.requestFocus();
                } else if (!binding.rbSaving.isChecked() && !binding.rbBorrowing.isChecked() && !binding.rbInvesting.isChecked()) {
                    Toast.makeText(ClubJoiningFormTwoActivity.this, "select motivation", Toast.LENGTH_SHORT).show();
                } else if (binding.incomeEt.getText().toString().isEmpty() || binding.incomeEt.getText().toString().length() < 4) {
                    binding.incomeEt.setError("enter your income");
                    binding.incomeEt.requestFocus();
                } else {
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
                    Log.i("USER_ENTERED",mobile);
                    String password = getIntent().getStringExtra("password");
                    Log.i("USER_ENTERED",password);
                    String name = getIntent().getStringExtra("full_name");
                    Log.i("USER_ENTERED",name);
                    String gender = getIntent().getStringExtra("gender");
                    Log.i("USER_ENTERED",gender);
                    String city = getIntent().getStringExtra("city");
                    Log.i("USER_ENTERED",city);
                    String occupation = getIntent().getStringExtra("occupation");
                    Log.i("USER_ENTERED",occupation);
                    String organization = getIntent().getStringExtra("organization");
                    Log.i("USER_ENTERED",organization);
                    String amount = binding.amountEt.getText().toString();
                    Log.i("USER_ENTERED",amount);
                    String income = binding.incomeEt.getText().toString();
                    Log.i("USER_ENTERED",income);

                    Call<Response> call = apiInterface.postItems(mobile, password, name, gender, city, occupation, organization, amount, motive, income);

                    call.enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            if (response.isSuccessful()) {
                                Response resp = response.body();
                                Result res = resp.getResult();
                                int IdOfUserCreated=res.getId();
                                Intent intent=new Intent(ClubJoiningFormTwoActivity.this,KycDetailsActivity.class);
                                intent.putExtra("id",String.valueOf(IdOfUserCreated));
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(ClubJoiningFormTwoActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {
                            Toast.makeText(ClubJoiningFormTwoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });


    }
}