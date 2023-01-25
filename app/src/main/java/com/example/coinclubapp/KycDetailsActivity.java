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

import com.example.coinclubapp.databinding.ActivityKycDetailsBinding;

import java.util.Calendar;

public class KycDetailsActivity extends AppCompatActivity {
    ActivityKycDetailsBinding binding;

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
                startActivity(new Intent(KycDetailsActivity.this,MainActivity.class));
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
        }
        else if(resultCode==RESULT_OK && requestCode==102)
        {
            binding.backImage.setImageURI(data.getData());
        }
    }


}