package com.diegoparra.gamescanner.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diegoparra.gamescanner.databinding.FragmentHomeBinding;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.utils.Resource;
import com.diegoparra.gamescanner.utils.ViewUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private DealsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        adapter = new DealsAdapter();
        binding.dealsList.setHasFixedSize(true);
        binding.dealsList.setAdapter(adapter);

        subscribeObservers();
    }

    private void subscribeObservers() {
        viewModel.getDealWithGameInfoList().observe(getViewLifecycleOwner(), new Observer<Resource<List<DealWithGameInfo>>>() {
            @Override
            public void onChanged(Resource<List<DealWithGameInfo>> listResource) {
                ViewUtils.isVisible(binding.progressCircular, listResource.getStatus() == Resource.Status.LOADING);
                ViewUtils.isVisible(binding.dealsList, listResource.getStatus() == Resource.Status.SUCCESS);
                ViewUtils.isVisible(binding.errorMessage, listResource.getStatus() == Resource.Status.ERROR);

                switch (listResource.getStatus()) {
                    case LOADING: break;
                    case SUCCESS: {
                        List<DealWithGameInfo> data = listResource.getData();
                        adapter.submitList(data);
                        break;
                    }
                    case ERROR: {
                        adapter.submitList(Collections.emptyList());
                        Throwable error = listResource.getError();
                        Objects.requireNonNull(error);
                        binding.errorMessage.setText(error.getMessage());
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}