package com.example.coinclubapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.coinclubapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BidNowFragment extends BottomSheetDialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate (R.layout.fragment_bid_now, container, false);
    }
}