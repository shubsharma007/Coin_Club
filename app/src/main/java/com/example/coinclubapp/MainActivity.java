package com.example.coinclubapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.coinclubapp.Adapters.HotClubAdapter;
import com.example.coinclubapp.Adapters.MyClubsAdapter;


import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Response.AllClubsGet;
import com.example.coinclubapp.Response.ClubInviteResponse;
import com.example.coinclubapp.Response.ProfileResponse;
import com.example.coinclubapp.Response.UserClubResponse;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.pusher.pushnotifications.PushNotifications;

import java.util.List;

import de.hdodenhof.circleimageview.BuildConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private long pressedTime;
    private final int STORAGE_PERMISSION_CODE = 295;
    int Id;
    ApiInterface apiInterface;
    Dialog adDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        String clubname = "", totalamount = "", totalmember = "", perhead = "", clubimage = "";
        int clubid = 0, userid = 0;

        //Storage Permission
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            requestStoragePermission();
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
//            requestPermission();
        }



        // additional data in notification
        if (getIntent() != null && getIntent().hasExtra("invite")) {
            adDialog = new Dialog(MainActivity.this);

            for (String key : getIntent().getExtras().keySet()) {
                Log.d("all keys", "key is " + key + ", value is " + getIntent().getExtras().get(key));
                if (key.equals("clubname")) {
                    clubname = (String) getIntent().getExtras().get(key);
                } else if (key.equals("totalamount")) {
                    totalamount = (String) getIntent().getExtras().get(key);

                } else if (key.equals("totalmember")) {
                    totalmember = (String) getIntent().getExtras().get(key);

                } else if (key.equals("perhead")) {

                    perhead = String.format("%.3f", Double.valueOf((String) getIntent().getExtras().get(key)));

                } else if (key.equals("clubimage")) {
                    clubimage = (String) getIntent().getExtras().get(key);
                } else if (key.equals("clubid")) {
                    clubid = Integer.parseInt((String) getIntent().getExtras().get(key));
                } else if (key.equals("userid")) {
                    userid = Integer.parseInt((String) getIntent().getExtras().get(key));
                }
            }

            adDialog.setContentView(R.layout.club_joining_popup_layout);
            adDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            adDialog.show();

            TextView cn = adDialog.findViewById(R.id.clubNameTv);
            TextView tm = adDialog.findViewById(R.id.totalMemberTv);
            TextView ta = adDialog.findViewById(R.id.totalAmountTv);
            TextView ph = adDialog.findViewById(R.id.perHeadTv);
            ImageView clubImg = adDialog.findViewById(R.id.clubimage);

            cn.setText(clubname);
            tm.setText(totalmember);
            ta.setText(totalamount);
            ph.setText(perhead);
            Glide.with(getApplicationContext()).load(clubimage).placeholder(R.drawable.invited).into(clubImg);

            TextView acceptBtn = adDialog.findViewById(R.id.acceptBtn);
            TextView rejectBtn = adDialog.findViewById(R.id.rejectBtn);


            int finalClubid = clubid;
            int finalUserid = userid;
            acceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Call<ClubInviteResponse> joinclubCall = apiInterface.joinClub(finalClubid, finalUserid, true);
                    joinclubCall.enqueue(new Callback<ClubInviteResponse>() {
                        @Override
                        public void onResponse(Call<ClubInviteResponse> call, Response<ClubInviteResponse> response) {
                            if (response.isSuccessful()) {
                                startActivity(new Intent(MainActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Log.d("ERROR", response.code() + "    " + response.errorBody().toString());
                                Toast.makeText(MainActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ClubInviteResponse> call, Throwable t) {
                            Log.d("ERROR", t.getMessage());

                            Toast.makeText(MainActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });

            adDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    finish();
                }
            });

            rejectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adDialog.dismiss();
                }
            });
        }

        Id = sharedPreferences.getInt("Id", 0);
        //FirebaseMessaging.getInstance().subscribeToTopic("song");
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                Log.d("dghsdghsddhhd", "onSuccess: "+s);
            }
        });


//        PushNotifications.start(getApplicationContext(), "53566661-977b-4140-b02f-cfbdb4b591a0");
//        PushNotifications.addDeviceInterest("hello");

        binding.recyclerViewClubs.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        binding.progressBar.setVisibility(View.VISIBLE);


        Call<List<UserClubResponse>> call = apiInterface.getClubsByUserId(Id);
        call.enqueue(new Callback<List<UserClubResponse>>() {
            @Override
            public void onResponse(Call<List<UserClubResponse>> call, Response<List<UserClubResponse>> response) {
                if (response.isSuccessful()) {
                    UserClubResponse resp = response.body().get(0);

                    binding.recyclerViewClubs.setAdapter(new MyClubsAdapter(MainActivity.this, resp.getUserclub()));
                    binding.progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(MainActivity.this, "some error occured", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserClubResponse>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failure,Try Again", Toast.LENGTH_SHORT).show();
            }
        });


        binding.notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NotificationsActivity.class));
            }
        });

        binding.fab.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), HotClubActivity.class)));

        binding.bottomNavView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.settings:
                    Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                    intent.putExtra("password", "fsdf");
                    startActivity(new Intent(getApplicationContext(), SettingActivity.class));
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
                    case R.id.nav_clubHistory:
                        startActivity(new Intent(getApplicationContext(), ClubHistoryActivity.class));
                        break;

                    case R.id.nav_AddBankDetails:
                        startActivity(new Intent(getApplicationContext(), BankDetailsActivity.class));
                        break;
                    case R.id.nav_customerSupport:
                        startActivity(new Intent(getApplicationContext(), CustomerSupportActivity.class));
                        break;

                    case R.id.nav_commission:
                        startActivity(new Intent(getApplicationContext(), CommissionActivity.class));
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
                    case R.id.nav_logout:
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        startActivity(intent);
                        finish();
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
//                Toast.makeText(this, "Permission Denied...", Toast.LENGTH_SHORT).show();
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