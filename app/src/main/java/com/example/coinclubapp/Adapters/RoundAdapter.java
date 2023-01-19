package com.example.coinclubapp.Adapters;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinclubapp.R;

public class RoundAdapter extends RecyclerView.Adapter<RoundAdapter.RoundViewHolder> {
    @NonNull
    @Override
    public RoundAdapter.RoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.round_layout,parent,false);
        return new RoundAdapter.RoundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoundAdapter.RoundViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
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
