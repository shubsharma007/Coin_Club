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
import com.example.coinclubapp.NotificationsActivity;
import com.example.coinclubapp.R;
import com.example.coinclubapp.Response.NotificationListModel;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {



    List<NotificationListModel> myNotifications;
    Context context;

    public NotificationAdapter(List<NotificationListModel> myNotifications, Context context) {
        this.myNotifications = myNotifications;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.notification_layout,parent,false);
        return new NotificationAdapter.NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, int position) {
        NotificationListModel singleOne=myNotifications.get(position);
        Glide.with(context).load("http://meetjob.techpanda.art"+singleOne.getImage()).placeholder(R.drawable.paisa).into(holder.dp_Img);
        holder.senderTv.setText(singleOne.getTitle());
        holder.msgTv.setText(singleOne.getBody());
    }

    @Override
    public int getItemCount() {
        return myNotifications.size();
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
