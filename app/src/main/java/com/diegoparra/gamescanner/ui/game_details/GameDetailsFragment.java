package com.diegoparra.gamescanner.ui.game_details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diegoparra.gamescanner.R;
import com.diegoparra.gamescanner.databinding.FragmentGameDetailsBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class GameDetailsFragment extends Fragment {

    private GameDetailsViewModel viewModel;
    private FragmentGameDetailsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(GameDetailsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGameDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}