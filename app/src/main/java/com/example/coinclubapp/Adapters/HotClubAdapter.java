package com.example.coinclubapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coinclubapp.Club_Activity;
import com.example.coinclubapp.InterFace.HotClubRecyclerView;
import com.example.coinclubapp.R;
import com.example.coinclubapp.Response.ClubResponse;
import com.example.coinclubapp.result.ClubResult;

import java.util.List;

public class HotClubAdapter extends RecyclerView.Adapter<HotClubAdapter.MyViewHolder> {
    Context context;
    ClubResponse clubResponse;
    List<ClubResult> clubResultList;
    String baseUrlImg="";

    public HotClubAdapter(Context context, ClubResponse clubResponse) {
        this.context = context;
        this.clubResponse = clubResponse;
        clubResultList=clubResponse.getClub();
        baseUrlImg=clubResponse.getImg() + "/";
    }

    @NonNull
    @Override
    public HotClubAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.hot_club_cardview_layout, parent, false);

        return new HotClubAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotClubAdapter.MyViewHolder holder, int position) {

        ClubResult current=clubResultList.get(position);
        holder.txtName.setText(current.getClub_name());
        holder.txtDesc.setText("member contribution : " + current.getMember_contribution());
        holder.txtRound.setText(current.getTotal_member());
        holder.txtAmount.setText(current.getTotal_amount());
        Glide.with(context)
                .load(baseUrlImg+current.getLogo())
                .centerCrop()
                .placeholder(R.drawable.logo_money)
                .into(holder.logo);


        holder.Club_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Club_Activity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clubResultList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtDesc, txtNextBid, txtRound, txtTime, txtAmount;
        ConstraintLayout Club_layout;
        ImageView logo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            txtNextBid = itemView.findViewById(R.id.txtNextBid);
            txtRound = itemView.findViewById(R.id.txtRound);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            Club_layout=itemView.findViewById(R.id.Club_layout);
            logo=itemView.findViewById(R.id.logo);




//            itemView.setOnClickListener(v -> {
//
//                if (hotClubRecyclerView != null) {
//
//                    int pos = getAdapterPosition();
//
//                    if (pos != RecyclerView.NO_POSITION) ;
//                    hotClubRecyclerView.onItemClick(pos);
//
//                }
//
//            });
        }

    }

}
