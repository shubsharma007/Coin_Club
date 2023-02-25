package com.example.coinclubapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinclubapp.R;

public class Average_round_Adapter extends RecyclerView.Adapter<Average_round_Adapter.MyViewHolder> {



    @NonNull
    @Override
    public Average_round_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.average_round_rv_cardview,parent,false);
        return new Average_round_Adapter.MyViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull Average_round_Adapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_round_detail;

        public MyViewHolder(@NonNull View itemView) {
            super (itemView);
            tv_round_detail = itemView.findViewById (R.id.tv_round_detail);
        }
    }
}
