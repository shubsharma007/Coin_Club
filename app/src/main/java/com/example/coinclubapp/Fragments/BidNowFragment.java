package com.example.coinclubapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.coinclubapp.BidRoomActivity;
import com.example.coinclubapp.BiddingModel.Bidders;
import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.R;
import com.example.coinclubapp.Response.ProfileResponse;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.databinding.FragmentBidNowBinding;
import com.example.coinclubapp.result.RoundsResult;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;

public class BidNowFragment extends BottomSheetDialogFragment {

    FragmentBidNowBinding binding;

    ApiInterface apiInterface;
    String roundName, clubName;
    int roundId;

    int val;
    int responsemax;
    int responsemin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBidNowBinding.inflate(inflater, container, false);
        apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);
        int Id = this.getArguments().getInt("Id");
        roundName = this.getArguments().getString("roundName");
        clubName = this.getArguments().getString("clubName");
        roundId=this.getArguments().getInt("roundId");

        binding.myBidButton.setOnClickListener(v -> {
            if (binding.bidAmtEt.getText().toString().trim().isEmpty()) {
                Toast.makeText(getActivity(), "Please enter your amount...", Toast.LENGTH_SHORT).show();
                binding.bidAmtEt.requestFocus();
            } else {
                String amount = binding.bidAmtEt.getText().toString();


                Call<RoundsResult> call=apiInterface.getRoundsById(roundId);
                call.enqueue(new Callback<RoundsResult>() {
                    @Override
                    public void onResponse(Call<RoundsResult> call, Response<RoundsResult> response) {
                        if(response.isSuccessful())
                        {
                             val=Integer.parseInt(amount);
                             responsemax=Integer.parseInt(String.valueOf(response.body().getMaxbid()));
                             responsemin=Integer.parseInt(String.valueOf(response.body().getMinbid()));
                            if(val>responsemax)
                            {
                                Toast.makeText(getContext(), "Max amount for bidding is different today, try again", Toast.LENGTH_SHORT).show();
                            }
                            else if (val<responsemin)
                            {
                                Toast.makeText(getContext(), "enter amount more than ₹ "+ responsemin, Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                bid(amount, Id);
                                binding.bidAmtEt.setText("");
                                BidNowFragment.this.dismiss();
                            }

                        }
                        else
                        {
                            Toast.makeText(getContext(), "Some Error Occured , Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RoundsResult> call, Throwable t) {
                        Toast.makeText(getContext(), "Some Failure Occured , Try Again", Toast.LENGTH_SHORT).show();
                    }
                });


            }

        });

        return binding.getRoot();
    }

    private void bid(String amtamt, int Id) {

        Integer amount = Integer.parseInt(amtamt);
        Call<ProfileResponse> call = apiInterface.getProfileItemById(Id);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()) {
                    DatabaseReference myReference = FirebaseDatabase.getInstance().getReference("Club1");
                    String myKey = String.valueOf(response.body().getMobileno());
                    Bidders bidders = new Bidders(response.body().getId(), response.body().getFullName(), amount);
                    myReference.child("Club1 round 1").child(myKey).setValue(bidders);
                } else {
                    Log.i("sdfjsifnsd", response.message());
                }
            }
            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Log.i("hduifnhsdfi", t.getMessage());
            }
        });
    }
}