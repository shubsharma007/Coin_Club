package com.example.coinclubapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coinclubapp.ClubActivity;
import com.example.coinclubapp.InsideClubHistoryActivity;
import com.example.coinclubapp.R;
import com.example.coinclubapp.result.Userclub;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ClubHistoryAdapter extends RecyclerView.Adapter<ClubHistoryAdapter.MyClubHistoryViewHolder> {

    Context context;
    List<Userclub> userclubs;
    List<Userclub> myUserClubs;

    public ClubHistoryAdapter(Context context, List<Userclub> userclubs) {
        this.context = context;
        this.userclubs = userclubs;
        myUserClubs=new ArrayList<>();

        for(Userclub my:userclubs)
        {
            if(my.getIsCompleted())
            {
                myUserClubs.add(my);
            }
        }
    }

    @NonNull
    @Override
    public ClubHistoryAdapter.MyClubHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.club_history_layout, parent, false);
        return new MyClubHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubHistoryAdapter.MyClubHistoryViewHolder holder, int position) {
        Userclub current = myUserClubs.get(position);

        holder.txtName.setText(current.getClubname());

        holder.txtDesc.setText("per head : " + String.format("%.3f", Double.valueOf(current.getClubcontribution())) + " ₹ ");
        holder.txtRound.setText("members " + current.getClubmembers());
        holder.txtAmount.setText(current.getClubamount() + " ₹");
        holder.txtNextBid.setText("Next Bid : " + current.getStartdate());
        Glide.with(context)
                .load("http://meetjob.techpanda.art" + current.getClubimage())
                .centerCrop()
                .placeholder(R.drawable.logo_money)
                .into(holder.logo);

        holder.Club_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InsideClubHistoryActivity.class);
                intent.putExtra("clubName", current.getClubname());
                intent.putExtra("clubAmount", current.getClubamount());
                intent.putExtra("perHead", current.getClubcontribution());
                intent.putExtra("nextBid", current.getStartdate());
                intent.putExtra("number", current.getClubmembers());
                intent.putExtra("clubId", current.getClubid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myUserClubs.size();
    }

    public static class MyClubHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtDesc, txtNextBid, txtRound, txtAmount;
        ConstraintLayout Club_layout;
        ImageView logo;
        TextView tv_days, tv_hour, tv_minute, tv_second, startBiddingTv;
        LinearLayout ll1, ll2, ll3, ll4;

        public MyClubHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            txtNextBid = itemView.findViewById(R.id.txtNextBid);
            txtRound = itemView.findViewById(R.id.txtRound);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            Club_layout = itemView.findViewById(R.id.Club_layout);
            logo = itemView.findViewById(R.id.logo);
            tv_days = itemView.findViewById(R.id.tv_days);
            tv_hour = itemView.findViewById(R.id.tv_hour);
            tv_minute = itemView.findViewById(R.id.tv_minute);
            tv_second = itemView.findViewById(R.id.tv_second);
            startBiddingTv = itemView.findViewById(R.id.startBiddingTv);
            ll1 = itemView.findViewById(R.id.ll1);
            ll2 = itemView.findViewById(R.id.ll2);
            ll3 = itemView.findViewById(R.id.ll3);
            ll4 = itemView.findViewById(R.id.ll4);
        }
    }
}