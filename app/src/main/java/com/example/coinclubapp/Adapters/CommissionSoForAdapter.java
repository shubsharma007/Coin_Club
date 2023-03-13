package com.example.coinclubapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinclubapp.R;
import com.example.coinclubapp.Response.CommissionSoFarResponse;

import java.util.List;

public class CommissionSoForAdapter extends RecyclerView.Adapter<CommissionSoForAdapter.MyViewHolder> {
    List<CommissionSoFarResponse> commissionSoFarResponseList;

    public CommissionSoForAdapter(List<CommissionSoFarResponse> commissionSoFarResponseList) {
        this.commissionSoFarResponseList = commissionSoFarResponseList;
    }

    @NonNull
    @Override
    public CommissionSoForAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_commission_tt_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommissionSoForAdapter.MyViewHolder holder, int position) {

        CommissionSoFarResponse singleUnit = commissionSoFarResponseList.get(position);
        singleUnit.getCommission();
        holder.tv1.setText(singleUnit.getAccount_tran());
        holder.tv2.setText(singleUnit.getCommission());
    }

    @Override
    public int getItemCount() {
        return commissionSoFarResponseList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
        }
    }
}
