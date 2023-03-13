package com.example.coinclubapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.R;
import com.example.coinclubapp.Response.ProfileResponse;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.result.RoundsResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OldRoundsAdapter extends RecyclerView.Adapter<OldRoundsAdapter.OldRoundsViewHolder>
{
    List<RoundsResult> myRounds;
    Context context;
    ApiInterface apiInterface;

    public OldRoundsAdapter(List<RoundsResult> myRounds, Context context) {
        this.myRounds = myRounds;
        this.context = context;
        apiInterface= RetrofitService.getRetrofit().create(ApiInterface.class);
    }

    @NonNull
    @Override
    public OldRoundsAdapter.OldRoundsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.round_layout, parent, false);
        return new OldRoundsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OldRoundsAdapter.OldRoundsViewHolder holder, int position) {
        RoundsResult result = myRounds.get(position);

        holder.roundBtn.setText(result.getRoundno());

        holder.nameTv.setText(String.valueOf(result.getWinner()));
        Call<ProfileResponse> call = apiInterface.getProfileItemById(Integer.parseInt(result.getWinner()));
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()) {
                    holder.amountTv.setText(String.valueOf(result.getRoundamount() + " ₹"));
                    holder.nameTv.setText(response.body().getFullName());
                } else {
                    Log.d("ERROR", response.message());
                }

            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
            }
        });


    }

    @Override
    public int getItemCount() {
        return myRounds.size();
    }

    public class OldRoundsViewHolder extends RecyclerView.ViewHolder {
        AppCompatButton roundBtn;
        TextView nameTv, amountTv;

        public OldRoundsViewHolder(@NonNull View itemView) {
            super(itemView);
            roundBtn = itemView.findViewById(R.id.roundBtn);
            nameTv = itemView.findViewById(R.id.nameTv);
            amountTv = itemView.findViewById(R.id.amountTv);
        }
    }
}
