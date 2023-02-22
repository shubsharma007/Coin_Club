package com.example.coinclubapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.coinclubapp.MainActivity;
import com.example.coinclubapp.R;
import com.example.coinclubapp.databinding.FragmentChangePinBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ChangePinFragment extends BottomSheetDialogFragment {
    FragmentChangePinBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangePinBinding.inflate(inflater, container, false);

        binding.btnSave.setOnClickListener(v -> {
            if (binding.oldPinET.getText().toString().trim().isEmpty()) {
                Toast.makeText(getActivity(), "Enter your old pin...", Toast.LENGTH_SHORT).show();
                binding.oldPinET.requestFocus();
            } else if (binding.newPinET.getText().toString().trim().isEmpty()) {
                Toast.makeText(getActivity(), "Enter your new pin...", Toast.LENGTH_SHORT).show();
                binding.newPinET.requestFocus();
            } else {
                Toast.makeText(getActivity(), "Your Password has been changed...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }
}

