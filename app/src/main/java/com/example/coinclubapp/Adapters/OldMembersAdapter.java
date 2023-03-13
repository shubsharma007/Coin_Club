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
import com.example.coinclubapp.InsideClubHistoryActivity;
import com.example.coinclubapp.R;
import com.example.coinclubapp.result.Clubuser;

import java.util.List;

public class OldMembersAdapter extends RecyclerView.Adapter<OldMembersAdapter.OldMemberViewHolder> {
    Context context;
    List<Clubuser> clubusers;

    public OldMembersAdapter(Context context, List<Clubuser> clubusers) {
        this.context = context;
        this.clubusers = clubusers;
    }

    @NonNull
    @Override
    public OldMembersAdapter.OldMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.member_layout,parent,false);
        return new OldMemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OldMembersAdapter.OldMemberViewHolder holder, int position) {
        Clubuser result=clubusers.get(position);
        Glide.with(context).load("http://meetjob.techpanda.art"+result.getUserprofileimg()).placeholder(R.drawable.avatar).into(holder.dp_img);
        holder.nameTv.setText(result.getUser());
    }

    @Override
    public int getItemCount() {
        return clubusers.size();
    }

    public class OldMemberViewHolder extends RecyclerView.ViewHolder {
        ImageView dp_img;
        TextView nameTv;
        //        View dot;
        ImageView winner_batch;
        public OldMemberViewHolder(@NonNull View itemView) {
            super(itemView);
            dp_img=itemView.findViewById(R.id.dp_img);
            nameTv=itemView.findViewById(R.id.nameTv);
//            dot=itemView.findViewById(R.id.dot);
            winner_batch=itemView.findViewById(R.id.winner_batch);
        }
    }
}
