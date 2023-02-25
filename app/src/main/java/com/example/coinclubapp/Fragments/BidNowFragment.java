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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBidNowBinding.inflate(inflater, container, false);
        apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);
        int Id = this.getArguments().getInt("Id");
        roundName = this.getArguments().getString("roundName");
        clubName = this.getArguments().getString("clubName");

        binding.myBidButton.setOnClickListener(v -> {
            if (binding.bidAmtEt.getText().toString().trim().isEmpty()) {
                Toast.makeText(getActivity(), "Please enter your amount...", Toast.LENGTH_SHORT).show();
                binding.bidAmtEt.requestFocus();
            } else {
                String amount = binding.bidAmtEt.getText().toString();
                Toast.makeText(getActivity(), "Your Amount " + amount, Toast.LENGTH_SHORT).show();

                bid(amount, Id);
                binding.bidAmtEt.setText("");
                this.dismiss();
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
// yogesh change change
                    DatabaseReference myReference = FirebaseDatabase.getInstance().getReference(clubName);
                    String myKey = String.valueOf(response.body().getMobileno());

                    Bidders bidders = new Bidders(response.body().getId(), response.body().getFullName(), amount);
                    myReference.child(roundName).child(myKey).setValue(bidders);

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