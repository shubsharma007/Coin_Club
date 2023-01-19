package com.example.coinclubapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinclubapp.R;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {
    @NonNull
    @Override
    public MemberAdapter.MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.member_layout,parent,false);
        return new MemberAdapter.MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.MemberViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder {
        ImageView dp_img;
        TextView nameTv;
        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            dp_img=itemView.findViewById(R.id.dp_img);
            nameTv=itemView.findViewById(R.id.nameTv);
        }
    }
}
