package com.example.coinclubapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coinclubapp.BiddingModel.Bidders;
import com.example.coinclubapp.R;
import com.example.coinclubapp.Response.AllUserProfilesGet;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    Context context;
    List<AllUserProfilesGet> resultList;

    public MemberAdapter(Context context, List<AllUserProfilesGet> resultList) {
        this.context = context;
        this.resultList = resultList;

//        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("CopyRight");
//        reference.setValue("Yogesh gurjar");
//        reference.setValue("Shajapur");

    }


    @NonNull
    @Override
    public MemberAdapter.MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.member_layout,parent,false);
        return new MemberAdapter.MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.MemberViewHolder holder, int position) {
        AllUserProfilesGet result=resultList.get(position);
        Glide.with(context).load("https://jobmeet.techpanda.art"+result.getProfileimg()).placeholder(R.drawable.avatar).into(holder.dp_img);
        holder.nameTv.setText(result.getFullName());



        if(position==0)
        {
            holder.winner_batch.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.winner_batch.setVisibility(View.GONE);
        }

//        if(result.getGender().equalsIgnoreCase("male"))
//        {
//            holder.dot.setBackgroundResource(R.drawable.online_user);
//        }
//        else
//        {
//            holder.dot.setBackgroundResource(R.drawable.offline_user);
//        }
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder {
        ImageView dp_img;
        TextView nameTv;
//        View dot;
        ImageView winner_batch;
        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            dp_img=itemView.findViewById(R.id.dp_img);
            nameTv=itemView.findViewById(R.id.nameTv);
//            dot=itemView.findViewById(R.id.dot);
            winner_batch=itemView.findViewById(R.id.winner_batch);
        }
    }
}