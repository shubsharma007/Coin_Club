package com.example.coinclubapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.MainActivity;
import com.example.coinclubapp.R;
import com.example.coinclubapp.Response.ProfileResponse;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.FragmentChangePinBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class ChangePinFragment extends BottomSheetDialogFragment {
    FragmentChangePinBinding binding;
    ApiInterface apiInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangePinBinding.inflate(inflater, container, false);
        int Id = this.getArguments().getInt("Id");
        apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);

        binding.btnSave.setOnClickListener(v -> {
            if (binding.oldPinET.getText().toString().trim().isEmpty()) {
                Toast.makeText(getActivity(), "Enter your old pin...", Toast.LENGTH_SHORT).show();
                binding.oldPinET.requestFocus();
            } else if (binding.newPinET.getText().toString().trim().isEmpty()) {
                Toast.makeText(getActivity(), "Enter your new pin...", Toast.LENGTH_SHORT).show();
                binding.newPinET.requestFocus();
            } else {

                Call<ProfileResponse> call = apiInterface.getProfileItemById(Id);
                call.enqueue(new Callback<ProfileResponse>() {
                    @Override
                    public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                        if (response.isSuccessful()) {
                            String oldPw = response.body().getPassword();
                            if (binding.newPinET.getText().toString().equals(oldPw)) {
                                Toast.makeText(getActivity(), "Old And New Password Should Be Different,Try Another Password", Toast.LENGTH_SHORT).show();
                            } else if (binding.newPinET.getText().toString().equals(binding.oldPinET.getText().toString())) {
                                Toast.makeText(getActivity(), "old and new password should not be same", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Your Password Has Been Changed Successfully...", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);

//                                Call<ProfileResponse> callPw=apiInterface.changePasswordPatch(Id,binding.newPinET.getText().toString());
//                                callPw.enqueue(new Callback<ProfileResponse>() {
//                                    @Override
//                                    public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
//                                        if (response.isSuccessful())
//                                        {
//                                            Toast.makeText(getActivity(), "Your Password Has Been Changed Successfully...", Toast.LENGTH_SHORT).show();
//                                            Intent intent = new Intent(getActivity(), MainActivity.class);
//                                            startActivity(intent);
//                                        }
//                                        else
//                                        {
//                                            Toast.makeText(getActivity(), "Some Error Occured", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<ProfileResponse> call, Throwable t) {
//                                        Toast.makeText(getActivity(), "Some Error Occured", Toast.LENGTH_SHORT).show();
//                                    }
//                                });

                            }
                        } else {
                            Toast.makeText(getActivity(), "Some Error Occured,Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProfileResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), "Some Failure Occured,Try Again After A While", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        return binding.getRoot();
    }
}

