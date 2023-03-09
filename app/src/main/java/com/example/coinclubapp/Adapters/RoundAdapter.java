package com.example.coinclubapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.R;
import com.example.coinclubapp.Response.ClubUserResponse;
import com.example.coinclubapp.Response.ProfileResponse;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.result.RoundsResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RoundAdapter extends RecyclerView.Adapter<RoundAdapter.RoundViewHolder> {

    Context context;
    List<RoundsResult> listOfRounds;
    int activeRound, clubId;

    ApiInterface apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);

    public RoundAdapter(List<RoundsResult> listOfRounds, int activeRound, int clubId, Context context) {
        this.context = context;
        this.listOfRounds = listOfRounds;
        this.activeRound = activeRound;
        clubId = clubId;

    }

    @NonNull
    @Override
    public RoundAdapter.RoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.round_layout, parent, false);
        return new RoundAdapter.RoundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoundAdapter.RoundViewHolder holder, int position) {
        RoundsResult result = listOfRounds.get(position);

        if (result.getId() == activeRound) {
            holder.roundBtn.setBackgroundResource(R.drawable.bg_round_count_dark);
            holder.roundBtn.setTextColor(Color.WHITE);
        }
        holder.roundBtn.setText(result.getRoundno());

        holder.nameTv.setText(String.valueOf(result.getWinner()));

        if (result.getWinner().equals("0") && result.getRoundamount().equals("0")) {
            holder.amountTv.setText("");
            holder.nameTv.setText("");
        } else {
            Call<ProfileResponse> call = apiInterface.getProfileItemById(Integer.parseInt(result.getWinner()));
            call.enqueue(new Callback<ProfileResponse>() {
                @Override
                public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                    if (response.isSuccessful()) {
                        holder.amountTv.setText(String.valueOf(result.getRoundamount()));
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

    }

    @Override
    public int getItemCount() {
        return listOfRounds.size();
    }

    public class RoundViewHolder extends RecyclerView.ViewHolder {
        AppCompatButton roundBtn;
        TextView nameTv, amountTv;

        public RoundViewHolder(@NonNull View itemView) {
            super(itemView);
            roundBtn = itemView.findViewById(R.id.roundBtn);
            nameTv = itemView.findViewById(R.id.nameTv);
            amountTv = itemView.findViewById(R.id.amountTv);
        }
    }
}
