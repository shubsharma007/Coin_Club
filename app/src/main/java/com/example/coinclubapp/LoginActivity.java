package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.coinclubapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.goBtn.setOnClickListener(v -> {
            if(binding.edMobile.getText ().toString ().trim ().isEmpty ()) {
                binding.edMobile.setError ("Please Enter Mobile Number");
                binding.edMobile.requestFocus ();
            }else if(binding.edMobile.getText ().toString ().trim ().length () < 10){
                binding.edMobile.setError ("Enter Correct Mobile Number");
                binding.edMobile.requestFocus ();
            } else if(binding.edPassword.getText ().toString ().trim ().isEmpty ()){
                binding.edPassword.setError ("Enter Password Number");
                binding.edPassword.requestFocus ();
            } else {
                Intent i = new Intent (LoginActivity.this, ClubJoiningFormOneActivity.class);
                String mobile=binding.edMobile.getText().toString();
                String pw=binding.edPassword.getText().toString();
                Log.i("USER_ENTERED",mobile);
                Log.i("USER_ENTERED",pw);
                i.putExtra("mobile",mobile);
                i.putExtra("password",pw);
                startActivity (i);
            }
        });
    }
}