package com.example.coinclubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Response.UserLoginResponse;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private final int STORAGE_PERMISSION_CODE = 295;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);

        //Storage Permission
        if (ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            requestStoragePermission();
        }

        if(ContextCompat.checkSelfPermission(LoginActivity.this,Manifest.permission.POST_NOTIFICATIONS)==PackageManager.PERMISSION_GRANTED)
        {
        }
        else
        {
            requestNotificationPermission();

        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
//            requestPermission();
        }

        ApiInterface apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);

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

                String mobileno = binding.edMobile.getText().toString();
                String password = binding.edPassword.getText().toString();

                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                Call<UserLoginResponse> call = apiInterface.loginUser(mobileno, password);
                call.enqueue(new Callback<UserLoginResponse>() {
                    @Override
                    public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            String status = response.body().getStatus();
                            if (status.equalsIgnoreCase("True")) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                Integer loginId = response.body().getId();
                                editor.putInt("Id", loginId);
                                editor.putString("number",mobileno);
                                editor.apply();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();

                                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                                Log.i("IP_ADDRESS", Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress()));

                                startActivity(i);
                                finish();
                            } else if (status.equalsIgnoreCase("false")) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "You are not authorized by the admin", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        if(t.getMessage().equalsIgnoreCase("not found"))
                        {
                            String notFound="Invalid Credentials";
                            Toast.makeText(LoginActivity.this, notFound, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        binding.btnSignUp.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, ClubJoiningFormOneActivity.class));
        });
    }

    private void requestNotificationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This permission is needed")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);

                        }
                    }).setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
        }
    }

    //Storage Permission Code...
    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This permission is needed")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

                        }
                    }).setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "permission Granted...", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(this, "Permission Denied...", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode==100)
        {
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else
            {

            }
        }

    }

}