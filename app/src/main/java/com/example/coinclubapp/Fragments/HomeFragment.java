package com.example.coinclubapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coinclubapp.R;
import com.example.coinclubapp.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

  FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentHomeBinding.inflate(inflater,container,false);




        return binding.getRoot();
    }
}