package com.example.coinclubapp.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinclubapp.R;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;

public class IssuesAdapter extends RecyclerView.Adapter<IssuesAdapter.IssuesViewHolder> {

        Boolean[] mamle;

    public IssuesAdapter(Boolean[] mamle) {
        this.mamle = mamle;
    }

    @NonNull
    @Override
    public IssuesAdapter.IssuesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.issues_layout, parent, false);
        return new IssuesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IssuesAdapter.IssuesViewHolder holder, int position) {


        holder.tv1.setText("Issue no.- " + String.valueOf(position+1));
        if(!mamle[position])
        {
            holder.image.setImageResource(R.drawable.ic_pending3);
            holder.tv.setText("Pending");
            holder.tv.setTextColor((Color.RED));
        }
        else
        {
            holder.image.setImageResource(R.drawable.ic_resolved3);
            holder.tv.setText("Resolved");
            holder.tv.setTextColor((Color.GREEN));
        }
    }

    @Override
    public int getItemCount() {
        return mamle.length;
    }

    public class IssuesViewHolder extends RecyclerView.ViewHolder {
        TextView tv, tv1;
//        CircleImageView image;
        GifImageView image;

        public IssuesViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
            tv1 = itemView.findViewById(R.id.tv1);
            image = itemView.findViewById(R.id.image);
        }
    }
}
