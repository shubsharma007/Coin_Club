package com.example.coinclubapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinclubapp.MainActivity;
import com.example.coinclubapp.R;
import com.example.coinclubapp.Response.PaidUnpaidListResponse;

import java.util.List;

public class LosersAdapter extends RecyclerView.Adapter<LosersAdapter.LosersViewHolder> {

    Context context;
    List<PaidUnpaidListResponse> listOfPaidUnpaid;

    public LosersAdapter(List<PaidUnpaidListResponse> listOfPaidUnpaid,Context context) {
        this.listOfPaidUnpaid = listOfPaidUnpaid;
        this.context=context;
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

        PaidUnpaidListResponse singleUnit=listOfPaidUnpaid.get(position);
        holder.senderTv.setText(singleUnit.getLooser().getFullName());

        singleUnit.getLooser().getFullName();
        holder.msgTv.setText("Amount  ₹"+ singleUnit.getRoundpayment().getRoundamount());

        if (!singleUnit.getIsPaid()) {
            holder.payBtn.setBackgroundResource(R.drawable.bg_red);
            holder.payBtn.setText("Pending");
        }
        else
        {
            holder.payBtn.setBackgroundResource(R.drawable.bg_green);
            holder.payBtn.setText("Recieved");
        }

        holder.senderTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MainActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfPaidUnpaid.size();
    }

    public class LosersViewHolder extends RecyclerView.ViewHolder {
        TextView senderTv,msgTv;
        AppCompatButton payBtn;
        public LosersViewHolder(@NonNull View itemView) {
            super(itemView);
            payBtn=itemView.findViewById(R.id.payBtn);
            senderTv=itemView.findViewById(R.id.senderTv);
            msgTv=itemView.findViewById(R.id.msgTv);
        }
    }
}
