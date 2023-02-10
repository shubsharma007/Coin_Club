package com.example.coinclubapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.coinclubapp.Adapters.HotClubAdapter;
import com.example.coinclubapp.HotClubActivity;
import com.example.coinclubapp.InterFace.ApiInterface;
import com.example.coinclubapp.MainActivity;
import com.example.coinclubapp.MyBankActivity;
import com.example.coinclubapp.NotificationsActivity;
import com.example.coinclubapp.ProfileActivity;
import com.example.coinclubapp.R;
import com.example.coinclubapp.Retrofit.RetrofitService;
import com.example.coinclubapp.SettingActivity;
import com.example.coinclubapp.databinding.FragmentHotClubBinding;
import com.example.coinclubapp.result.ClubResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HotClubFragment extends Fragment {
    FragmentHotClubBinding binding;
    RecyclerView.LayoutManager layoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHotClubBinding.inflate(inflater, container, false);

        binding.progressBar.setVisibility(View.VISIBLE);

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);

        binding.btnSetting.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SettingActivity.class));
        });

        ApiInterface apiInterface = RetrofitService.getRetrofit().create(ApiInterface.class);
        Call<List<ClubResult>> call = apiInterface.getAllClubs();
        call.enqueue(new Callback<List<ClubResult>>() {
            @Override
            public void onResponse(Call<List<ClubResult>> call, Response<List<ClubResult>> response) {
                if (response.isSuccessful()) {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.recyclerView.setAdapter(new HotClubAdapter(getContext(), response.body()));
                } else {
                    Toast.makeText(getContext(), "some error occured", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ClubResult>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();

    }
}