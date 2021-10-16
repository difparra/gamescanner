package com.diegoparra.gamescanner.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.diegoparra.gamescanner.R;
import com.diegoparra.gamescanner.databinding.FragmentHomeBinding;
import com.diegoparra.gamescanner.models.DealWithGameInfo;
import com.diegoparra.gamescanner.ui.shared.DealWithGameInfoAdapter;
import com.diegoparra.gamescanner.utils.EventObserver;
import com.diegoparra.gamescanner.utils.NavigationUtils;
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
    private DealWithGameInfoAdapter adapter;

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
        setupToolbar();
        setupDealsList();
        subscribeObservers();
    }


    private void setupToolbar() {
        NavigationUtils.setupToolbar(binding.toolbar);
        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_item_search) {
                    NavDirections navDirections = HomeFragmentDirections.actionHomeFragmentToSearchFragment();
                    NavHostFragment.findNavController(HomeFragment.this).navigate(navDirections);
                }
                return true;
            }
        });
    }

    private void setupDealsList() {
        adapter = new DealWithGameInfoAdapter(dealId -> viewModel.navigateDetails(dealId));
        binding.dealsList.setHasFixedSize(true);
        binding.dealsList.setAdapter(adapter);
        binding.dealsList.addItemDecoration(new DividerItemDecoration(binding.dealsList.getContext(), DividerItemDecoration.VERTICAL));
    }

    private void subscribeObservers() {
        viewModel.getDealWithGameInfoList().observe(getViewLifecycleOwner(), new Observer<Resource<List<DealWithGameInfo>>>() {
            @Override
            public void onChanged(Resource<List<DealWithGameInfo>> listResource) {
                ViewUtils.isVisible(binding.progressCircular, listResource.getStatus() == Resource.Status.LOADING);
                ViewUtils.isVisible(binding.dealsList, listResource.getStatus() == Resource.Status.SUCCESS);
                ViewUtils.isVisible(binding.errorMessage, listResource.getStatus() == Resource.Status.ERROR);

                switch (listResource.getStatus()) {
                    case LOADING:
                        break;
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
        viewModel.getNavigateDetails().observe(getViewLifecycleOwner(), new EventObserver<>(dealId -> {
            NavDirections navDirections = HomeFragmentDirections.actionGlobalGameDetailsFragment(dealId);
            NavHostFragment.findNavController(HomeFragment.this).navigate(navDirections);
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}