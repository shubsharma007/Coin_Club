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

import com.example.coinclubapp.R;

public class BidsAdapter extends RecyclerView.Adapter<BidsAdapter.BidsViewHolder> {
    public BidsAdapter() {
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
        holder.serialNoTv.setText(String.valueOf(position+1));
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class BidsViewHolder extends RecyclerView.ViewHolder {
        TextView serialNoTv;
        ConstraintLayout cl;
        public BidsViewHolder(@NonNull View itemView) {
            super(itemView);
           serialNoTv=itemView.findViewById(R.id.serialNoTv);
           cl=itemView.findViewById(R.id.cl);
        }
    }
}
