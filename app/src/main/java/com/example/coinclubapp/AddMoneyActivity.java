package com.example.coinclubapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Response.AddOrWithdrawMoneyResponsePost;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityAddMoneyBinding;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMoneyActivity extends AppCompatActivity {
    ActivityAddMoneyBinding binding;

    String amount;
    int Id;

    Uri imageUri;
    static String imageString;

    ApiInterface apiInterface;
    Dialog adDialog;
    boolean imageUploaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMoneyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);

        apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adDialog = new Dialog(AddMoneyActivity.this);
                amount = binding.amountEt.getText().toString();
                Id = sharedPreferences.getInt("Id", 0);
                if (binding.amountEt.getText().toString().isEmpty()) {
                    binding.amountEt.setError("please enter amount");
                    binding.amountEt.requestFocus();
                } else if (!imageUploaded) {
                    Toast.makeText(AddMoneyActivity.this, "Please Upload Image", Toast.LENGTH_SHORT).show();
                } else {
                    sendDetails(Id, amount, imageString);
                }
            }
        });

        binding.uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgIntent1 = new Intent(Intent.ACTION_PICK);
                imgIntent1.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imgIntent1, 201);
            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showPopup() {

        adDialog.setContentView(R.layout.add_money_popup);
        adDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        adDialog.show();

        AppCompatButton okBtn = adDialog.findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddMoneyActivity.this, MainActivity.class));
                finish();
            }
        });
        adDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                startActivity(new Intent(AddMoneyActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void sendDetails(int id, String amount, String imageString) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        File file = new File(imageString);

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("walletimg", file.getName(), requestBody);

        RequestBody totalamount = RequestBody.create(MediaType.parse("text/plain"), amount);
        RequestBody userwallet = RequestBody.create(MediaType.parse("text/plain"), Integer.toString(Id));

        Call<AddOrWithdrawMoneyResponsePost> call = apiInterface.postAddMoney(part, totalamount, userwallet);
        call.enqueue(new Callback<AddOrWithdrawMoneyResponsePost>() {
            @Override
            public void onResponse(Call<AddOrWithdrawMoneyResponsePost> call, Response<AddOrWithdrawMoneyResponsePost> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    showPopup();
                    Log.i("hnukdjfnhsd",response.message());
                } else {
                    Log.i("hnukdjfnhsd",response.message());
                    Toast.makeText(AddMoneyActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddOrWithdrawMoneyResponsePost> call, Throwable t) {
                Log.i("uhfikdhsdjkf",t.getMessage() + t.getCause().getMessage() + t.getLocalizedMessage());
                Toast.makeText(AddMoneyActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 201) {
            binding.uploadImage.setImageURI(data.getData());
            imageUploaded = true;
            imageUri = data.getData();
            imageString = getRealPathFromURI(imageUri);
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}