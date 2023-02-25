package com.example.coinclubapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coinclubapp.BiddingModel.Bidders;
import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.R;
import com.example.coinclubapp.Response.ProfileResponse;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdapter extends FirebaseRecyclerAdapter<Bidders, MyAdapter.MyViewHolder> {
    ApiInterface apiInterface;
    Context context;

    public MyAdapter(@NonNull FirebaseRecyclerOptions<Bidders> options,Context context) {
        super(options);
        apiInterface= RetrofitService.getRetrofit().create(ApiInterface.class);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Bidders model) {
        holder.senderTv.setText(model.getName());
        holder.amountTv.setText(String.valueOf(model.getBiddingAmount()));
        Call<ProfileResponse> call=apiInterface.getProfileItemById(model.getId());
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if(response.isSuccessful())
                {
                    Glide.with(context).load("https://jobmeet.techpanda.art"+response.body().getProfileimg()).placeholder(R.drawable.avatar).into(holder.dp_img);
                }
                else
                {
                    Toast.makeText(context, "Profile images not available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Log.i("dfnsdkjfsnf",t.getMessage());
                Toast.makeText(context, "profile pictures are unavailable due to server failure", Toast.LENGTH_SHORT).show();
            }
        });

//      if (position == 0) {
//            holder.cl.setBackgroundColor(Color.parseColor("#D9DDFF"));
//        } else {
//            holder.cl.setBackgroundResource(R.color.white);
//        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.bid_member_layout, parent, false);
        return new MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout cl;
        TextView senderTv;
        AppCompatTextView amountTv;
        CircleImageView dp_img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cl = itemView.findViewById(R.id.cl);
            senderTv = itemView.findViewById(R.id.senderTv);
            amountTv = itemView.findViewById(R.id.amountTextView);
            dp_img=itemView.findViewById(R.id.dp_img);
        }
    }
}
