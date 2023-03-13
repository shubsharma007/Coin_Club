package com.example.coinclubapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coinclubapp.R;
import com.example.coinclubapp.result.Transc;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>{

    Context context;
    List<Transc> transcList;
    public TransactionAdapter(Context context, List<Transc> transcList){
        this.context = context;
        this.transcList=transcList;
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
        Transc singleUnit=transcList.get(position);
        if(singleUnit.getCategory().equalsIgnoreCase("transection_to"))
        {
            holder.imageView5.setImageResource(R.drawable.ic_already_paid);
            holder.statusTv.setText("Paid");
            holder.transactionIdTv.setText(String.valueOf(singleUnit.getId()));
            holder.nameTv.setText("to " + singleUnit.getWinner());
            holder.amountTv.setText(singleUnit.getPayamount() + " ₹");
            holder.statusTv.setTextColor(Color.parseColor("#3CD9A0"));
        }
        else if(singleUnit.getCategory().equalsIgnoreCase("transection_by"))
        {
            holder.imageView5.setImageResource(R.drawable.rupee);

            holder.statusTv.setText("Recieved");
            holder.transactionIdTv.setText(String.valueOf(singleUnit.getId()));
            holder.nameTv.setText("from " + singleUnit.getLooser());
            holder.amountTv.setText(singleUnit.getPayamount() + " ₹");
            holder.statusTv.setTextColor(Color.parseColor("#FF0000"));
//            aya he
        }

    }

    @Override
    public int getItemCount() {
        return transcList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv,transactionIdTv,amountTv,statusTv;
        ImageView imageView5;
        public ViewHolder(@NonNull View itemView) {
            super (itemView);
            nameTv=itemView.findViewById(R.id.nameTv);
            transactionIdTv=itemView.findViewById(R.id.transactionIdTv);
            amountTv=itemView.findViewById(R.id.amountTv);
            statusTv=itemView.findViewById(R.id.statusTv);
            imageView5=itemView.findViewById(R.id.imageView5);
        }
    }
}
