package com.example.coinclubapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinclubapp.R;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>{

    Context context;
    public TransactionAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.transaction_cardview, parent,false);
        TransactionAdapter.ViewHolder viewHolder = new TransactionAdapter.ViewHolder (view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {
        if(position%2==0)
        {
            holder.tv_status_money.setText("Recieved");
            holder.tv_status_money.setTextColor(Color.parseColor("#3CD9A0"));
        }
        else if(position%3==0)
        {
            holder.tv_status_money.setText("Failed");
            holder.tv_status_money.setTextColor(Color.parseColor("#FF0000"));
        }
        else if(position%5==0)
        {
            holder.tv_status_money.setText("Paid");
            holder.tv_status_money.setTextColor(Color.parseColor("#FF9A1A"));
        }
        else
        {
            holder.tv_status_money.setTextColor(Color.parseColor("#1A7FFF"));
            holder.tv_status_money.setText("Pending");
        }
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_status_money;
        public ViewHolder(@NonNull View itemView) {
            super (itemView);
            tv_status_money=itemView.findViewById(R.id.tv_status_money);
        }
    }
}
