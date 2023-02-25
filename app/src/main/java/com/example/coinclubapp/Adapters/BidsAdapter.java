package com.example.coinclubapp.Adapters;

import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinclubapp.BiddingModel.Bidders;
import com.example.coinclubapp.R;

import java.util.List;

public class BidsAdapter extends RecyclerView.Adapter<BidsAdapter.BidsViewHolder> {
    List<Bidders> biddersList;

    public BidsAdapter(List<Bidders> biddersList) {
        if(biddersList!=null)
        {
            this.biddersList = biddersList;
        }

    }

    @NonNull
    @Override
    public BidsAdapter.BidsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.bid_member_layout,parent,false);
        return new BidsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BidsAdapter.BidsViewHolder holder, int position) {
        if (position == 0) {
            holder.cl.setBackgroundColor(Color.parseColor("#D9DDFF"));
        } else {
            holder.cl.setBackgroundResource(R.color.white);
        }

    }

    @Override
    public int getItemCount() {
        return biddersList.size();
    }

    public class BidsViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout cl;
        TextView senderTv,amountTv;
        public BidsViewHolder(@NonNull View itemView) {
            super(itemView);
           cl=itemView.findViewById(R.id.cl);
           senderTv=itemView.findViewById(R.id.senderTv);
           amountTv=itemView.findViewById(R.id.amountEt);
        }
    }
}
