package com.example.coinclubapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.coinclubapp.Adapters.IssuesAdapter;
import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Response.CustomerResponse;
import com.example.coinclubapp.Response.IssuesForSpinnerResponseGet;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityCustomerSupportBinding;
import com.example.coinclubapp.result.Issue;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerSupportActivity extends AppCompatActivity {
    ActivityCustomerSupportBinding binding;
    String issueSelected;
    ApiInterface apiInterface;
    Dialog adDialog;

    // this is to check ki issue resolve hue he k nhi
    Boolean[] mamle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerSupportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);
        adDialog = new Dialog(CustomerSupportActivity.this);


        Call<List<IssuesForSpinnerResponseGet>> call = apiInterface.getAllIssuesForSpinner();
        call.enqueue(new Callback<List<IssuesForSpinnerResponseGet>>() {
            @Override
            public void onResponse(Call<List<IssuesForSpinnerResponseGet>> call, Response<List<IssuesForSpinnerResponseGet>> response) {
                if (response.isSuccessful()) {
                    List<IssuesForSpinnerResponseGet> issueResponses = response.body();
                    String tamp = "Please select any Issue,,,";
                    for (int i = 0; i < issueResponses.size(); i++) {

                        tamp = tamp.concat(response.body().get(i).getIssue() + ",,,");
                        Log.i("jdiogjoifdjgijd",response.body().get(i).getIssue());
                        Log.i("jdiogjoifdjgijd",tamp);
                    }
                    String[] strArray = tamp.split(",,,");
                    Log.i("yogesh bhai", tamp);


                    ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, strArray);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    binding.spinner.setAdapter(aa);

                    binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            issueSelected = strArray[position];
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }

            }

            @Override
            public void onFailure(Call<List<IssuesForSpinnerResponseGet>> call, Throwable t) {
                Log.i("nhfuilsdnf", t.getMessage());
            }
        });


        Call<List<CustomerResponse>> call2 = apiInterface.getCustomerIssue();
        call2.enqueue(new Callback<List<CustomerResponse>>() {
            @Override
            public void onResponse(Call<List<CustomerResponse>> call, Response<List<CustomerResponse>> response) {
                if (response.isSuccessful()) {
                    List<CustomerResponse> customerResponses = response.body();
                    mamle = new Boolean[response.body().size()];
//                    customerResponses.get(0).getStatus();

                    for (int i = 0; i < customerResponses.size(); i++) {

                        mamle[i] = customerResponses.get(i).getStatus();
                    }


                    binding.recyclerview.setAdapter(new IssuesAdapter(mamle));
                    binding.recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


                }
            }

            @Override
            public void onFailure(Call<List<CustomerResponse>> call, Throwable t) {

            }
        });


        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String disc = binding.etDisc.getText().toString();
                String spinner = binding.spinner.getSelectedItem().toString();


                if (binding.etDisc.getText().toString().isEmpty()) {
                    binding.etDisc.setError("Please Type Your Issue's");
                    binding.etDisc.requestFocus();
                } else if (binding.spinner.getSelectedItem().equals("Please select any Issue")) {
                    Toast.makeText(CustomerSupportActivity.this, "Please Select Your Issue", Toast.LENGTH_SHORT).show();
                } else {
                    Call<Issue> call3 = apiInterface.postCustomerIssue(disc, spinner);
                    call3.enqueue(new Callback<Issue>() {
                        @Override
                        public void onResponse(Call<Issue> call, Response<Issue> response) {
                            if (response.isSuccessful()) {

                                showPopup();
//                                Toast.makeText(CustomerSupportActivity.this, "your Issue has been submitted to our team ," +
//                                        " please wait for 24 hours", Toast.LENGTH_SHORT).show();
                                Log.i("ISSUESELECTED", issueSelected);
                            } else {
                                Toast.makeText(CustomerSupportActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Issue> call, Throwable t) {
                            Toast.makeText(CustomerSupportActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


    private void showPopup() {

        adDialog.setContentView(R.layout.issue_popup);
        adDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        adDialog.show();

        AppCompatButton okBtn = adDialog.findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        adDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }

}