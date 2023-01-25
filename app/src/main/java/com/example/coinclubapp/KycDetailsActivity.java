package com.example.coinclubapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.coinclubapp.databinding.ActivityKycDetailsBinding;

import java.util.Calendar;

public class KycDetailsActivity extends AppCompatActivity {
    ActivityKycDetailsBinding binding;
    private static boolean frontimg=false;
    private static boolean backimg=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKycDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String name=getIntent().getStringExtra("full_name");
        binding.fnEt.setText(name);
        String mobile=getIntent().getStringExtra("mobile");
        binding.mnEt.setText(mobile);

        binding.checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName=binding.fnEt.getText().toString().trim();
                String[] arrOfStr = fullName.split(" ");
                String fullAddress=binding.aEt.getText().toString().trim();
                String[] arrOfStr2 = fullAddress.split(" ");
                if(binding.fnEt.getText().toString().isEmpty())
                {
                    binding.fnEt.setError("enter your name");
                    binding.fnEt.requestFocus();
                }
                else if(arrOfStr.length<2)
                {
                    binding.fnEt.setError("enter your full name");
                    binding.fnEt.requestFocus();
                }
                else if(binding.mnEt.getText().toString().trim().length()!=10){
                    binding.mnEt.setError("enter correct mobile no");
                    binding.mnEt.requestFocus();
                }
                else if(binding.aEt.getText().toString().isEmpty())
                {
                    binding.aEt.setError("enter your Address");
                    binding.aEt.requestFocus();
                }
                else if(arrOfStr2.length<2)
                {
                    binding.aEt.setError("enter your full Address");
                    binding.aEt.requestFocus();
                }
                else if(binding.mailEt.getText().toString().isEmpty())
                {
                    binding.mailEt.setError("enter your Email Id");
                    binding.mailEt.requestFocus();
                }
                else if(!binding.mailEt.getText().toString().contains(".") || !binding.mailEt.getText().toString().contains("@"))
                {
                    binding.mailEt.setError("enter full Email address");
                    binding.mailEt.requestFocus();
                }
                else if(!binding.radio1.isChecked() && !binding.radio2.isChecked() && !binding.radio3.isChecked())
                {
                    Toast.makeText(KycDetailsActivity.this, "enter identification", Toast.LENGTH_SHORT).show();
                }
                else if(binding.iEt.getText().toString().isEmpty())
                {
                    binding.iEt.setError("enter identification number");
                    binding.iEt.requestFocus();
                }
                else if(!frontimg || !backimg)
                {
                    Toast.makeText(KycDetailsActivity.this, "please upload images", Toast.LENGTH_SHORT).show();
                }
                else if(binding.lnEt.getText().toString().isEmpty())
                {
                    binding.lnEt.setError("enter license number");
                    binding.lnEt.requestFocus();
                }
                else if(binding.ledTv2.getText().toString().isEmpty())
                {
                    Toast.makeText(KycDetailsActivity.this, "enter expiry date", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    startActivity(new Intent(KycDetailsActivity.this,MainActivity.class));
                    Toast.makeText(KycDetailsActivity.this, "Kyc Successful", Toast.LENGTH_SHORT).show();
                    finish();
                }





            }
        });

        binding.frontImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgIntent1=new Intent(Intent.ACTION_PICK);
                imgIntent1.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imgIntent1,101);
            }
        });

        binding.backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgIntent2=new Intent(Intent.ACTION_PICK);
                imgIntent2.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imgIntent2,102);
            }
        });


        binding.ledTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(KycDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date= dayOfMonth + "-" + (month + 1) + "-" + (year);
                     binding.ledTv2.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK  && requestCode==101)
        {
            binding.frontImage.setImageURI(data.getData());
            frontimg=true;
        }
        else if(resultCode==RESULT_OK && requestCode==102)
        {
            binding.backImage.setImageURI(data.getData());
            backimg=true;
        }
    }


}