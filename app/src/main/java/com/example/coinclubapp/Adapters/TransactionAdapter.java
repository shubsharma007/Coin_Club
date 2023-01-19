package com.example.coinclubapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinclubapp.R;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>{

    Context context;
    public TransactionAdapter(Context context, Object o){
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

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super (itemView);
        }
    }
}
