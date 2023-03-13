package com.example.coinclubapp.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.KycDetailsActivity;
import com.example.coinclubapp.MainActivity;
import com.example.coinclubapp.R;
import com.example.coinclubapp.Response.ListToGetIdOfRecord;
import com.example.coinclubapp.Response.PaidUnpaidListResponse;
import com.example.coinclubapp.Response.RoundCompletedPatchResponse;
import com.example.coinclubapp.Retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.Path;

public class LosersAdapter extends RecyclerView.Adapter<LosersAdapter.LosersViewHolder> {

    Context context;
    List<PaidUnpaidListResponse> listOfPaidUnpaid;
    List<Boolean> toShowDialog;
    int roundId;
    ApiInterface apiInterface;

    public LosersAdapter(List<PaidUnpaidListResponse> listOfPaidUnpaid,Context context,int roundId) {
        this.listOfPaidUnpaid = listOfPaidUnpaid;
        this.context=context;
        this.roundId=roundId;
        toShowDialog=new ArrayList<>();
        apiInterface= RetrofitService.getRetrofit().create(ApiInterface.class);
        for(PaidUnpaidListResponse single: listOfPaidUnpaid)
        {
            if (!single.getIsPaid()) {
               toShowDialog.add(false);
            }
            else
            {
                toShowDialog.add(true);
            }
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            if(toShowDialog.isEmpty() || toShowDialog.stream().allMatch(toShowDialog.get(0)::equals))
            {
                // show ad dialog in this condition
                showPopup1(context);
            }
        }

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

    private void showPopup1(Context context) {
        Dialog adDialog1;
        adDialog1 = new Dialog(context);
        adDialog1.setContentView(R.layout.payment_received_popup);
        adDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        adDialog1.show();

        AppCompatButton yesBtn = adDialog1.findViewById(R.id.yesBtn);
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adDialog1.dismiss();
                    showPopup2(context);
            }
        });
        AppCompatButton noBtn=adDialog1.findViewById(R.id.noBtn);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adDialog1.dismiss();
            }
        });
    }

    private void showPopup2(Context context) {
        Dialog adDialog2;
        adDialog2 = new Dialog(context);
        adDialog2.setContentView(R.layout.next_round_popup);
        adDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        adDialog2.show();

        AppCompatButton yesBtn = adDialog2.findViewById(R.id.yesBtn);
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<RoundCompletedPatchResponse> call=apiInterface.setRoundCompletedPatchById(roundId,true,true);
                call.enqueue(new Callback<RoundCompletedPatchResponse>() {
                    @Override
                    public void onResponse(Call<RoundCompletedPatchResponse> call, Response<RoundCompletedPatchResponse> response) {
                        if(response.isSuccessful())
                        {
                            adDialog2.dismiss();
                            context.startActivity(new Intent(context,MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(context, "Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<RoundCompletedPatchResponse> call, Throwable t) {
                        Toast.makeText(context, "Try Again", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        AppCompatButton noBtn=adDialog2.findViewById(R.id.noBtn);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adDialog2.dismiss();
            }
        });
    }
}
