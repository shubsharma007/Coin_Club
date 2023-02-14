package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.coinclubapp.Adapters.IssuesAdapter;
import com.example.coinclubapp.databinding.ActivityCustomerSupportBinding;

public class CustomerSupportActivity extends AppCompatActivity {
    ActivityCustomerSupportBinding binding;
    String issueSelected;
    String[] issues = { "Please select any issue","Having trouble in adding money", "Withdraw money issues", "Club joining issues", "Missed a round of current club", "Not recieving round notification","Contact to our customer support team","Want to raise a comaplaint ticket","other issues"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerSupportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerview.setAdapter(new IssuesAdapter());
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));


        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, issues);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        binding.spinner.setAdapter(aa);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 issueSelected=issues[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomerSupportActivity.this, "your issue has been submitted to our team , please wait for 24 hours", Toast.LENGTH_SHORT).show();
                Log.i("ISSUESELECTED",issueSelected);
            }
        });
    }
}