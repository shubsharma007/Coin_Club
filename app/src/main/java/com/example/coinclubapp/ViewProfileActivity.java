package com.example.coinclubapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.RealPathUtilGithub.RealPathUtil;
import com.example.coinclubapp.Response.PatchProfileResponse;
import com.example.coinclubapp.Response.ProfileResponse;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityViewProfileBinding;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class ViewProfileActivity extends AppCompatActivity {
    ActivityViewProfileBinding binding;
    ApiInterface apiInterface;

    private static boolean imageLeAaeFromGallery = false;

    private static String imagePath;

    SharedPreferences sharedPreferences;
    int Id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        Id = sharedPreferences.getInt("Id", 0);

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

            imageLeAaeFromGallery = true;

            imagePath = RealPathUtil.getRealPath(ViewProfileActivity.this, data.getData());

            File file = new File(imagePath);

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part profileimg = MultipartBody.Part.createFormData("document_image", file.getName(), requestFile);

            Call<PatchProfileResponse> callPatchDp= apiInterface.patchProfileById(Id,profileimg);
            callPatchDp.enqueue(new Callback<PatchProfileResponse>() {
                @Override
                public void onResponse(Call<PatchProfileResponse> call, Response<PatchProfileResponse> response) {
                    if(response.isSuccessful())
                    {
                        Log.d("ERROR",response.body().getMsg());
                        Toast.makeText(ViewProfileActivity.this, "profile picture updated", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(ViewProfileActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                        Log.d("ERROR",response.body() + response.message() +response.code());
                    }
                }

                @Override
                public void onFailure(Call<PatchProfileResponse> call, Throwable t) {
                    Toast.makeText(ViewProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("ERROR",t.getMessage());
                }
            });
            imageLeAaeFromGallery = false;

        }
    }
}