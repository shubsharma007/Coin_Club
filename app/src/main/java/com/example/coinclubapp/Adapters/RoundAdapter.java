package com.example.coinclubapp.Adapters;

import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinclubapp.R;
import com.example.coinclubapp.result.RoundsResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RoundAdapter extends RecyclerView.Adapter<RoundAdapter.RoundViewHolder> {

    List<RoundsResult> listOfRounds;
    int activeRound;

    public RoundAdapter(List<RoundsResult> listOfRounds,int activeRound) {
        this.listOfRounds = listOfRounds;
        this.activeRound=activeRound;
    }

    @NonNull
    @Override
    public RoundAdapter.RoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.round_layout,parent,false);
        return new RoundAdapter.RoundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoundAdapter.RoundViewHolder holder, int position) {
        RoundsResult result=listOfRounds.get(position);

        if(result.getId()==activeRound)
        {
            holder.roundBtn.setBackgroundResource(R.drawable.bg_round_count_dark);
            holder.roundBtn.setTextColor(Color.WHITE);
        }
        holder.roundBtn.setText(result.getRoundno());

        result.getWinner().toString();
        holder.nameTv.setText(String.valueOf(result.getWinner()));
        if(result.getWinner().equals("0"))
        {
            holder.amountTv.setText("");
        }
        else
        {
            holder.amountTv.setText(String.valueOf(result.getRoundamount()));
        }
        if(result.getWinner().equals("0"))
        {
            holder.nameTv.setText("");
        }
        else
        {
            holder.nameTv.setText(result.getWinner().toString());
        }

    }

    @Override
    public int getItemCount() {
        return listOfRounds.size();
    }

    public class RoundViewHolder extends RecyclerView.ViewHolder {
        AppCompatButton roundBtn;
        TextView nameTv,amountTv;
        public RoundViewHolder(@NonNull View itemView) {
            super(itemView);
            roundBtn=itemView.findViewById(R.id.roundBtn);
            nameTv=itemView.findViewById(R.id.nameTv);
            amountTv=itemView.findViewById(R.id.amountTv);
        }
    }
}
