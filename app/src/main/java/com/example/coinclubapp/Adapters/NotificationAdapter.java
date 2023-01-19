package com.example.coinclubapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinclubapp.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {


    @NonNull
    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.notification_layout,parent,false);
        return new NotificationAdapter.NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        ImageView dp_Img;
        TextView senderTv,timeTv,msgTv;
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            dp_Img=(ImageView) itemView.findViewById(R.id.dp_img);
            senderTv=(TextView) itemView.findViewById(R.id.senderTv);
            timeTv=(TextView) itemView.findViewById(R.id.timeTv);
            msgTv=(TextView) itemView.findViewById(R.id.msgTv);
        }
    }
}
