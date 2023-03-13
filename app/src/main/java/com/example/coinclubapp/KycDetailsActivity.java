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
import com.example.coinclubapp.Response.KycResponse;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityKycDetailsBinding;


import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KycDetailsActivity extends AppCompatActivity {
    ActivityKycDetailsBinding binding;

    private static boolean frontaadhar = false;
    private static boolean backaadhar = false;
    private static boolean frontpan = false;

    Uri aadharFront;
    Uri aadharBack;
    Uri panFront;

    Dialog adDialog;
    String uriafs, uriabs, uripfs;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKycDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        int Id;
        Id = sharedPreferences.getInt("Id", 0);

        binding.appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.checkoutBtn.setOnClickListener(v -> {
            String fullName = binding.fullNameEt.getText().toString().trim();
            String[] arrOfStr = fullName.split(" ");

            String mobile = binding.mobileEt.getText().toString();

            String address = binding.addressEt.getText().toString().trim();
            String[] arrOfStr2 = address.split(" ");

            String email_id = binding.emailEt.getText().toString();

            String adhar_no = binding.adharNumberEt.getText().toString();
            String pan_no = binding.panNumberEt.getText().toString();
//            String license_no = binding.licenseNumberEt.getText().toString();
//            String licenseExpiryDate = binding.licenseExpiryDateEt.getText().toString();
            adDialog = new Dialog(KycDetailsActivity.this);

            if (binding.fullNameEt.getText().toString().isEmpty()) {
                binding.fullNameEt.setError("enter your name");
                binding.fullNameEt.requestFocus();
            } else if (arrOfStr.length < 2) {
                binding.fullNameEt.setError("enter your full name");
                binding.fullNameEt.requestFocus();
            } else if (binding.mobileEt.getText().toString().trim().length() != 10) {
                binding.mobileEt.setError("enter correct mobile no");
                binding.mobileEt.requestFocus();
            } else if (binding.addressEt.getText().toString().isEmpty()) {
                binding.addressEt.setError("enter your Address");
                binding.addressEt.requestFocus();
            } else if (arrOfStr2.length < 2) {
                binding.addressEt.setError("enter your full Address");
                binding.addressEt.requestFocus();
            } else if (binding.emailEt.getText().toString().isEmpty()) {
                binding.emailEt.setError("enter your Email Id");
                binding.emailEt.requestFocus();
            } else if (!binding.emailEt.getText().toString().contains(".") || !binding.emailEt.getText().toString().contains("@")) {
                binding.emailEt.setError("enter full Email address");
                binding.emailEt.requestFocus();
            } else if (binding.adharNumberEt.getText().toString().isEmpty()) {
                binding.adharNumberEt.setError("enter Aadhar Number");
                binding.adharNumberEt.requestFocus();
            } else if (binding.panNumberEt.getText().toString().isEmpty()) {
                binding.panNumberEt.setError("enter Pan Number");
                binding.panNumberEt.requestFocus();
            } else if (!frontaadhar || !backaadhar) {
                Toast.makeText(KycDetailsActivity.this, "please upload Aadhar Images", Toast.LENGTH_SHORT).show();
            } else if (!frontpan) {
                Toast.makeText(KycDetailsActivity.this, "please upload Pan Images", Toast.LENGTH_SHORT).show();
            }
            else {

                sendDetails(Id,binding.fullNameEt.getText().toString(),mobile,address,email_id,adhar_no,pan_no,uriafs,uriabs,uripfs);
            }
        });

        binding.aadharFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgIntent1 = new Intent(Intent.ACTION_PICK);
                imgIntent1.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imgIntent1, 101);
            }
        });

        binding.aadharBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgIntent1 = new Intent(Intent.ACTION_PICK);
                imgIntent1.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imgIntent1, 102);
            }
        });

        binding.panFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgIntent1 = new Intent(Intent.ACTION_PICK);
                imgIntent1.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imgIntent1, 201);
            }
        });


    }

    private void sendDetails(Integer Id,String xName, String xMobile, String xAddress, String xEmail, String xAdharno, String xPanno, String uriafs, String uriabs, String uripfs) {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        File adharF = new File (uriafs);
        File adharB=new File(uriabs);
        File panF=new File(uripfs);

        RequestBody adharFront = RequestBody.create (MediaType.parse ("image/*"), adharF);
        MultipartBody.Part adharfrontimg = MultipartBody.Part.createFormData ("aadharfrontimg", adharF.getName (), adharFront);

        RequestBody adharBack = RequestBody.create (MediaType.parse ("image/*"), adharB);
        MultipartBody.Part adharbackimg = MultipartBody.Part.createFormData ("aadharbackimg", adharB.getName (), adharBack);

        RequestBody panFront = RequestBody.create (MediaType.parse ("image/*"), panF);
        MultipartBody.Part panfrontimg = MultipartBody.Part.createFormData ("panfrontimg", panF.getName (), panFront);


        RequestBody fullname = RequestBody.create (MediaType.parse ("text/plain"), xName);
        RequestBody address = RequestBody.create (MediaType.parse ("text/plain"), xAddress);
        RequestBody mobile = RequestBody.create (MediaType.parse ("text/plain"), xMobile);
        RequestBody email = RequestBody.create (MediaType.parse ("text/plain"),xEmail);
        RequestBody aadharno = RequestBody.create (MediaType.parse ("text/plain"), xAdharno);
        RequestBody panno = RequestBody.create (MediaType.parse ("text/plain"), xPanno);
        RequestBody registeruser = RequestBody.create (MediaType.parse ("text/plain"),Integer.toString(Id));


        apiInterface=RetrofitService.getRetrofit().create(ApiInterface.class);
        Call<KycResponse> call=apiInterface.postKycItems(fullname,address,mobile,email,aadharno,panno,registeruser,adharfrontimg,adharbackimg,panfrontimg);
        call.enqueue(new Callback<KycResponse>() {
            @Override
            public void onResponse(Call<KycResponse> call, Response<KycResponse> response) {
                if(response.isSuccessful())
                {
                    progressDialog.dismiss();

                    showPopup();
                }
                else
                {
                    Toast.makeText(KycDetailsActivity.this, "some error occured", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KycResponse> call, Throwable t) {
                Log.i("hdkfhdfk",t.getMessage());
                Toast.makeText(KycDetailsActivity.this, "some error occured , please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPopup() {

        adDialog.setContentView(R.layout.kyc_pending_popup_layout);
        adDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        adDialog.show();

        AppCompatButton okBtn = adDialog.findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KycDetailsActivity.this,MainActivity.class));
                finish();
            }
        });
        adDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                startActivity(new Intent(KycDetailsActivity.this,MainActivity.class));
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 101) {
            binding.aadharFront.setImageURI(data.getData());
            frontaadhar = true;
            aadharFront = data.getData();
            uriafs = getRealPathFromURI(aadharFront);
        } else if (resultCode == RESULT_OK && requestCode == 102) {
            binding.aadharBack.setImageURI(data.getData());
            backaadhar = true;
            aadharBack = data.getData();
            uriabs = getRealPathFromURI(aadharBack);
        } else if (resultCode == RESULT_OK && requestCode == 201) {
            binding.panFront.setImageURI(data.getData());
            frontpan = true;
            panFront = data.getData();
            uripfs = getRealPathFromURI(panFront);
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