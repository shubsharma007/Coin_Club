package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.coinclubapp.databinding.ActivityClubJoiningFormTwoBinding;

public class ClubJoiningFormTwoActivity extends AppCompatActivity {
    ActivityClubJoiningFormTwoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityClubJoiningFormTwoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(binding.amountEt.getText().toString().isEmpty())
                {
                    binding.amountEt.setError("enter the amount");
                    binding.amountEt.requestFocus();
                }
                else if(!binding.rbSaving.isChecked() && !binding.rbBorrowing.isChecked() && !binding.rbInvesting.isChecked())
                {
                    Toast.makeText(ClubJoiningFormTwoActivity.this, "select motivation", Toast.LENGTH_SHORT).show();
                }
                else if (binding.incomeEt.getText().toString().isEmpty() || binding.incomeEt.getText().toString().length()<4)
                {
                    binding.incomeEt.setError("enter your income");
                    binding.incomeEt.requestFocus();
                }
                else {
                    startActivity(new Intent(ClubJoiningFormTwoActivity.this, KycDetailsActivity.class));
                }
            }
        });
    }
}