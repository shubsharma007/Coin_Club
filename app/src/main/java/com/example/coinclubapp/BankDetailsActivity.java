package com.example.coinclubapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.ActivityBankDetailsBinding;
import com.example.coinclubapp.result.BankDetailsResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankDetailsActivity extends AppCompatActivity {

    ActivityBankDetailsBinding binding;
    private static boolean bankPassbook = false;
    String uriPassBook=null;
    Uri bankPassbookFront;
//    String uriBankPassbook=null;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        binding = ActivityBankDetailsBinding.inflate(getLayoutInflater());
        setContentView (binding.getRoot());

        binding.backBtn.setOnClickListener (v -> {
            Intent i = new Intent (BankDetailsActivity.this, ProfileActivity.class);
            startActivity (i);
            finish ();
        });

        binding.submitBtn.setOnClickListener (v -> {
            String fullName = binding.accountHolderNameET.getText().toString().trim();
            String[] arrOfStr = fullName.split(" ");

            if (binding.accountHolderNameET.getText().toString().isEmpty()) {
                binding.accountHolderNameET.setError("Enter Your Name");
                binding.accountHolderNameET.requestFocus();
            } else if (arrOfStr.length < 2) {
                binding.accountHolderNameET.setError("Enter Your Full Name");
                binding.accountHolderNameET.requestFocus();
            } else if (binding.accountNumberET.getText ().toString ().isEmpty ()){
                binding.accountNumberET.setError("Enter Account Number");
                binding.accountNumberET.requestFocus();
            } else if (binding.ifscET.getText ().toString ().isEmpty ()){
                binding.accountNumberET.setError("Enter IFSC code");
                binding.accountNumberET.requestFocus();
            } else if (binding.registerMobileET.getText().toString().trim().length() != 10) {
                binding.registerMobileET.setError("Enter Correct Mobile No");
                binding.registerMobileET.requestFocus();
            }  else if (!bankPassbook) {
                Toast.makeText(BankDetailsActivity.this, "Please Upload Bank Passbook Photo", Toast.LENGTH_SHORT).show();
            } else if(!binding.check.isChecked()){
                Toast.makeText(BankDetailsActivity.this, "Please Select Checkbox", Toast.LENGTH_SHORT).show();
            } else {
                String holdername=binding.accountHolderNameET.getText().toString();
                String accountno=binding.accountNumberET.getText().toString();
                String ifsc=binding.ifscET.getText().toString();
                String mobileno=binding.registerMobileET.getText().toString();

                apiInterface= RetrofitService.getRetrofit().create(ApiInterface.class);

                Call<BankDetailsResult> call=apiInterface.postBankDetails(mobileno,ifsc,holdername,accountno,"uriBankPassbook".toString());
                call.enqueue(new Callback<BankDetailsResult>() {
                    @Override
                    public void onResponse(Call<BankDetailsResult> call, Response<BankDetailsResult> response) {
                        Intent i  = new Intent (BankDetailsActivity.this, MainActivity.class);
                            startActivity (i);
                            finish ();

//                        if(response.code()==201)
//                        {
//                            Intent i  = new Intent (BankDetailsActivity.this, MainActivity.class);
//                            startActivity (i);
//                            finish ();
//                        }
//                        else
//                        {
//                            Toast.makeText(BankDetailsActivity.this, "no resp", Toast.LENGTH_SHORT).show();
//                        }
                    }
                    @Override
                    public void onFailure(Call<BankDetailsResult> call, Throwable t) {
                        Toast.makeText(BankDetailsActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.BankPassbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgIntent1 = new Intent(Intent.ACTION_PICK);
                imgIntent1.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imgIntent1, 101);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult (requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 101) {
            binding.BankPassbook.setImageURI(data.getData());

            bankPassbookFront = data.getData();
            uriPassBook = getRealPathFromURI(bankPassbookFront);
            bankPassbook = true;
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