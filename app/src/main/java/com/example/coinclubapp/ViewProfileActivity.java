package com.example.coinclubapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Response.ProfileResponse;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityViewProfileBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProfileActivity extends AppCompatActivity {
    ActivityViewProfileBinding binding;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        int Id = sharedPreferences.getInt("Id", 0);


        apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);
        Call<ProfileResponse> call = apiInterface.getProfileItemById(Id);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()) {
                    String profile = String.valueOf(response.body().getProfileimg());
                    Glide.with(getApplicationContext()).load("http://meetjob.techpanda.art" + profile).placeholder(R.drawable.avatar).into(binding.dpImg);
                    binding.emailTv2.setText(response.body().getEmail());
                    binding.rnTv2.setText(response.body().getMobileno());
                    binding.anEt.setText(response.body().getAlternateno());
                    binding.nameTv.setText(response.body().getFullName());
                    binding.msTv2.setText(response.body().getCreateAt());
//                    binding.cityTv2.setText(response.body().getCity());
//                    binding.occupationTv2.setText(response.body().getOccupation());

                } else {
                    Toast.makeText(ViewProfileActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Toast.makeText(ViewProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.editBtn.setOnClickListener(v -> {
            Intent editImg = new Intent(Intent.ACTION_PICK);
            editImg.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(editImg, 123);
        });

        binding.backBtn.setOnClickListener(v ->
                finish());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 123) {
            binding.dpImg.setImageURI(data.getData());
        }
    }
}