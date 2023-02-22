package com.example.coinclubapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.coinclubapp.BidRoomActivity;
import com.example.coinclubapp.R;
import com.example.coinclubapp.databinding.FragmentBidNowBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BidNowFragment extends BottomSheetDialogFragment {

    FragmentBidNowBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBidNowBinding.inflate(inflater, container, false);

        binding.myBidButton.setOnClickListener(v -> {
            if (binding.bidAmtEt.getText().toString().trim().isEmpty()) {
                Toast.makeText(getActivity(), "Please enter your amount...", Toast.LENGTH_SHORT).show();
                binding.bidAmtEt.requestFocus();
            } else {
                String amount = binding.bidAmtEt.getText().toString();
                Toast.makeText(getActivity(),"Your Amount "+amount, Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getActivity(), BidRoomActivity.class));
                this.dismiss();
            }

        });

        return binding.getRoot();
    }
}