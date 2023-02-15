package com.example.coinclubapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.RealPathUtilGithub.RealPathUtil;
import com.example.coinclubapp.Response.BankResponsePost;
import com.example.coinclubapp.Retrofit.RetrofitService;

import com.example.coinclubapp.databinding.ActivityBankDetailsBinding;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankDetailsActivity extends AppCompatActivity {

    ActivityBankDetailsBinding binding;
    private static boolean bankPassbook = false;

    private static String imagePath;
    Integer Id;
    ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBankDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);

        binding.backBtn.setOnClickListener(v -> {

            finish();
        });

        binding.documentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imgIntent, 101);
            }
        });

        binding.submitBtn.setOnClickListener(v -> {


            String paytm = binding.paytmEt.getText().toString();
            String phonepe = binding.phonePeEt.getText().toString();
            String googlepay = binding.googlePayEt.getText().toString();
            String bhimupi = binding.bhimUpiEt.getText().toString();
            Id = sharedPreferences.getInt("Id", 0);
            sendDetails(Id, paytm, phonepe, googlepay, bhimupi);

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && data != null) {

            Uri uri = data.getData();
            Context context = BankDetailsActivity.this;
            imagePath = RealPathUtil.getRealPath(context, uri);
            binding.documentImage.setImageURI(uri);

            bankPassbook = true;
        }
    }

    private void sendDetails(Integer Id, String paytm, String phonepe, String googlepay, String bhimupi) {
        File file = new File(imagePath);
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
//        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part document_image = MultipartBody.Part.createFormData("document_image", file.getName(), requestFile);


//        RequestBody fBody = RequestBody.create(null, someFile);
//        service.uploadFile(fBody, "some_string_session", true);
        RequestBody registeruser = RequestBody.create(MediaType.parse("text/plain"), Integer.toString(Id));
        RequestBody googlepay_number = RequestBody.create(MediaType.parse("text/plain"), googlepay);
        RequestBody paytm_number = RequestBody.create(MediaType.parse("text/plain"), paytm);
        RequestBody phonepe_number = RequestBody.create(MediaType.parse("text/plain"), phonepe);
        RequestBody bhim_upi = RequestBody.create(MediaType.parse("text/plain"), bhimupi);

        apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);
        Call<BankResponsePost> call = apiInterface.postBankDetails(registeruser, googlepay_number, paytm_number, phonepe_number, bhim_upi, document_image);
        call.enqueue(new Callback<BankResponsePost>() {
            @Override
            public void onResponse(Call<BankResponsePost> call, Response<BankResponsePost> response) {

                if (response.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(BankDetailsActivity.this, "success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BankDetailsActivity.this, MainActivity.class));
                    finish();
                } else {
//                    Log.i("fhduifhduf",response.message());
//                    Log.i("fdsfsdfhsdf",response.errorBody().toString());
//                    Log.i("fsdfsdufsdf",String.valueOf(response.code()));
//                    Log.i("fdfklsdjfhsdjkfn",response.raw().toString());
                    Toast.makeText(BankDetailsActivity.this, "Error, Try Again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BankResponsePost> call, Throwable t) {
//                Toast.makeText(BankDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                Log.i("fhduifhduf",t.getMessage());
//                Log.i("fdsfsdfhsdf",t.getCause().toString());
                Toast.makeText(BankDetailsActivity.this, "Error,Try Again After Sometime", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
