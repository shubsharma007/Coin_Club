package com.example.coinclubapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coinclubapp.R;
import com.example.coinclubapp.Response.AllUserProfilesGet;
import com.example.coinclubapp.result.Clubuser;

import java.util.List;

public class SeeAllMembersAdapter extends RecyclerView.Adapter<SeeAllMembersAdapter.SeeAllMembersViewHolder> {

    List<Clubuser> resultList;
    Context context;

    public SeeAllMembersAdapter(Context context,List<Clubuser> resultList) {
        this.resultList = resultList;
        this.context=context;
    }

    @NonNull
    @Override
    public SeeAllMembersAdapter.SeeAllMembersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.see_all_member_layout,parent,false);

        return new SeeAllMembersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeeAllMembersAdapter.SeeAllMembersViewHolder holder, int position) {
        Clubuser result=resultList.get(position);
        Glide.with(context).load("http://meetjob.techpanda.art"+result.getUserprofileimg()).placeholder(R.drawable.avatar).into(holder.imageView);
        Log.d("imageimageimage",result.getUserprofileimg());

        holder.nameTv.setText(result.getUser());
        holder.cityTv.setText(result.getUsercity());
        holder.occupationTv.setText(result.getUseroccupation());
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class SeeAllMembersViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv,cityTv,occupationTv;
        ImageView imageView;
        public SeeAllMembersViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv=itemView.findViewById(R.id.nameTv);
            cityTv=itemView.findViewById(R.id.cityTv);
            occupationTv=itemView.findViewById(R.id.occupationTv);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
}
