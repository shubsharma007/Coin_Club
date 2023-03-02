package com.example.coinclubapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinclubapp.R;

public class LosersAdapter extends RecyclerView.Adapter<LosersAdapter.LosersViewHolder> {
    public LosersAdapter() {
    }

    @NonNull
    @Override
    public LosersAdapter.LosersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.payment_layout_for_winner, parent, false);
        return new LosersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LosersAdapter.LosersViewHolder holder, int position) {
        if (position % 2 == 0) {
            holder.payBtn.setBackgroundResource(R.drawable.bg_red);
            holder.payBtn.setText("Pending");
        }
        else
        {
            holder.payBtn.setBackgroundResource(R.drawable.bg_green);
            holder.payBtn.setText("Recieved");
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class LosersViewHolder extends RecyclerView.ViewHolder {
        AppCompatButton payBtn;
        public LosersViewHolder(@NonNull View itemView) {
            super(itemView);
            payBtn=itemView.findViewById(R.id.payBtn);
        }
    }
}
