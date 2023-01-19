package com.example.coinclubapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinclubapp.InterFace.HotClubRecyclerView;
import com.example.coinclubapp.R;

public class HotClubAdapter extends RecyclerView.Adapter<HotClubAdapter.MyViewHolder> {
    Context context;
    private final HotClubRecyclerView hotClubRecyclerView;

    public HotClubAdapter(Context context, HotClubRecyclerView hotClubRecyclerView) {
        this.context = context;
        this.hotClubRecyclerView = hotClubRecyclerView;
    }

    @NonNull
    @Override
    public HotClubAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.hot_club_cardview_layout, parent, false);

        return new MyViewHolder(view,hotClubRecyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull HotClubAdapter.MyViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtDesc, txtNextBid, txtRound, txtTime, txtAmount;

        public MyViewHolder(@NonNull View itemView, HotClubRecyclerView hotClubRecyclerView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            txtNextBid = itemView.findViewById(R.id.txtNextBid);
            txtRound = itemView.findViewById(R.id.txtRound);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtAmount = itemView.findViewById(R.id.txtAmount);

            itemView.setOnClickListener(v -> {

                if (hotClubRecyclerView != null) {

                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) ;
                    hotClubRecyclerView.onItemClick(pos);

                }

            });
        }

    }

}
