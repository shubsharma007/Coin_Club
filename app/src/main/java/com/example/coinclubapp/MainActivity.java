package com.example.coinclubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.coinclubapp.Adapters.HotClubAdapter;
import com.example.coinclubapp.Adapters.MyClubsAdapter;


import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Response.AllClubsGet;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import com.pusher.pushnotifications.PushNotifications;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private long pressedTime;
    private final int STORAGE_PERMISSION_CODE = 295;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);

        //Storage Permission
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            requestStoragePermission();
        }

        PushNotifications.start(getApplicationContext(), "53566661-977b-4140-b02f-cfbdb4b591a0");
        PushNotifications.addDeviceInterest("hello");

//        Intent serviceIntent = new Intent(getApplicationContext(), ExampleService.class);
//        startService(serviceIntent);

        binding.recyclerViewClubs.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        binding.progressBar.setVisibility(View.VISIBLE);
        Call<List<AllClubsGet>> call = apiInterface.getAllClubs();
        call.enqueue(new Callback<List<AllClubsGet>>() {
            @Override
            public void onResponse(Call<List<AllClubsGet>> call, Response<List<AllClubsGet>> response) {
                if (response.isSuccessful()) {

                    binding.recyclerViewClubs.setAdapter(new MyClubsAdapter(MainActivity.this, response.body()));
                    binding.progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(MainActivity.this, "some error occured", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AllClubsGet>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failure,Try Again", Toast.LENGTH_SHORT).show();
            }
        });


        binding.fab.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), HotClubActivity.class)));

        binding.bottomNavView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.notification:
                    startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));
                    break;
                case R.id.Dashboard:
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    break;
                case R.id.money:
                    startActivity(new Intent(getApplicationContext(), MyBankActivity.class));
                    break;
            }
            return false;
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar
                , 0, 0) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);

        binding.toolbar.setNavigationIcon(R.drawable.ic_menu);

        binding.drawernavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.nav_Profile:
                        startActivity(new Intent(getApplicationContext(), ViewProfileActivity.class));
                        break;
                    case R.id.nav_ActiveClubs:

                        break;
                    case R.id.nav_HistoricalClubs:

                        break;
                    case R.id.nav_AddBankDetails:
                        startActivity(new Intent(getApplicationContext(), BankDetailsActivity.class));
                        break;
                    case R.id.nav_MyBankAccounts:
                        startActivity(new Intent(getApplicationContext(), CustomerSupportActivity.class));
                        break;
                    case R.id.nav_TransactionHistory:

                        break;

                    case R.id.nav_ReferAndEarn:
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                        String shareMessage = "\nLet me recommend you this application\n\n";
                        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                        startActivity(Intent.createChooser(shareIntent, "choose one"));
                        break;
                    case R.id.nav_Setting:
                        startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                        break;
                    case R.id.nav_logout:
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finishAffinity();
                        break;
                }

                return false;
            }
        });
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
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

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
                Toast.makeText(this, "Permission Denied...", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            finish();
            super.onBackPressed();
            finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}