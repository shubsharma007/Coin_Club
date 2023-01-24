package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.coinclubapp.databinding.ActivityClubJoiningFormOneBinding;

public class ClubJoiningFormOneActivity extends AppCompatActivity {

    ActivityClubJoiningFormOneBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClubJoiningFormOneBinding.inflate (getLayoutInflater ());
        setContentView(binding.getRoot ());


        binding.rbSalary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {

                    binding.rbStudent.setChecked(false);
                    binding.rbSelfEmployed.setChecked(false);
                    binding.rbHousewife.setChecked(false);
                }
            }
        });
        binding.rbStudent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    binding.rbSalary.setChecked(false);
                    binding.rbSelfEmployed.setChecked(false);
                    binding.rbHousewife.setChecked(false);
                }
            }
        });
        binding.rbSelfEmployed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    binding.rbStudent.setChecked(false);
                    binding.rbSalary.setChecked(false);
                    binding.rbHousewife.setChecked(false);
                }
            }
        });
        binding.rbHousewife.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    binding.rbStudent.setChecked(false);
                    binding.rbSelfEmployed.setChecked(false);
                    binding.rbSalary.setChecked(false);
                }
            }
        });

        String name =  binding.fullNameEt.getText ().toString ().trim ();
        String city = binding.cityEt.getText ().toString ().trim ();
        String organisation = binding.noyoEt.getText ().toString ().trim ();

        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName=binding.fullNameEt.getText().toString().trim();
                String[] arrOfStr = fullName.split(" ");
                if(binding.fullNameEt.getText().toString().isEmpty())
                {
                    binding.fullNameEt.setError("enter your name");
                    binding.fullNameEt.requestFocus();
                }
                else if(arrOfStr.length<2)
                {
                    binding.fullNameEt.setError("enter your full name");
                    binding.fullNameEt.requestFocus();
                }
                else if(binding.mobileNoEt.getText().toString().trim().length()!=10){
                    binding.mobileNoEt.setError("enter correct mobile no");
                    binding.mobileNoEt.requestFocus();
                }
                else if(binding.cityEt.getText().toString().trim().isEmpty())
                {
                    binding.cityEt.setError("enter city name");
                    binding.cityEt.requestFocus();
                }
                else if(!binding.rbMale.isChecked() && !binding.rbFemale.isChecked())
                {
                    Toast.makeText(ClubJoiningFormOneActivity.this, "enter your gender", Toast.LENGTH_SHORT).show();
                }
                else if(!binding.rbSalary.isChecked() && !binding.rbHousewife.isChecked() && !binding.rbStudent.isChecked() && !binding.rbSelfEmployed.isChecked())
                {
                    Toast.makeText(ClubJoiningFormOneActivity.this, "please enter your occupation", Toast.LENGTH_SHORT).show();
                }
                else if(binding.noyoEt.getText().toString().isEmpty())
                {
                    binding.noyoEt.setError("enter organisation name");
                    binding.noyoEt.requestFocus();
                }
                else
                {
                    startActivity(new Intent(ClubJoiningFormOneActivity.this,ClubJoiningFormTwoActivity.class));

                }

            }
        });
    }
}