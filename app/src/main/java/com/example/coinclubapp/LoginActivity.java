package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Response.LoginResponse;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityLoginBinding;
import com.example.coinclubapp.result.LoginResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    Dialog adDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.goBtn.setOnClickListener(v -> {
            if (binding.edMobile.getText().toString().trim().isEmpty()) {
                binding.edMobile.setError("Please Enter Mobile Number");
                binding.edMobile.requestFocus();
            } else if (binding.edMobile.getText().toString().trim().length() < 10) {
                binding.edMobile.setError("Enter Correct Mobile Number");
                binding.edMobile.requestFocus();
            } else if (binding.edPassword.getText().toString().trim().isEmpty()) {
                binding.edPassword.setError("Enter Password Number");
                binding.edPassword.requestFocus();
            } else {

                String mobile = binding.edMobile.getText().toString();
                String password = binding.edPassword.getText().toString();
                ApiInterface apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);
                Call<LoginResponse> call = apiInterface.UserLogin(mobile, password);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            LoginResponse loginResponse = response.body();

                            if (loginResponse.getStatus()) {

                               // LoginResult login_result = loginResponse.getLogin_data();

                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                    Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                                    startActivity(i);

                            } else {

                                Toast.makeText(LoginActivity.this, "incorrect phone or password...", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });
        binding.btnSignUp.setOnClickListener(v -> {

            Intent i = new Intent(LoginActivity.this, ClubJoiningFormOneActivity.class);
//                String mobile=binding.edMobile.getText().toString();
//                String pw=binding.edPassword.getText().toString();
//                Log.i("USER_ENTERED",mobile);
//                Log.i("USER_ENTERED",pw);
//                i.putExtra("mobile",mobile);
//                i.putExtra("password",pw);
            startActivity(i);

        });
    }
}